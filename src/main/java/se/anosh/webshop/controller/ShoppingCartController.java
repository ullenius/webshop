package se.anosh.webshop.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Orderline;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.OrderService;
import se.anosh.webshop.service.ProductService;
import se.anosh.webshop.service.Shopping;
import se.anosh.webshop.service.ShoppingCart;
import se.anosh.webshop.service.UserService;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCartController {
	
	private final Shopping cart;
	private final ProductService productService;
	private final OrderService orderService;
	private final UserService userService;
	private static final int ZERO = 0;
	
	@Autowired
	public ShoppingCartController(ProductService productService, OrderService orderService, UserService userService) {
		this.productService = productService;
		this.orderService = orderService;
		this.userService = userService;
		cart = new ShoppingCart();
	}
	
	@GetMapping(value="/shoppingCart")
	public ModelAndView displayShoppingCart() {
		
		final Map<String,Object> contents = new HashMap<>();
		final Map<Product,Integer> shoppingCartView = createShoppingList();
		contents.put("products",shoppingCartView);
		contents.put("totalPrice",cart.calculateTotalPrice());
		
		return new ModelAndView("cart","model",contents);
	}
	
	@PostMapping(value="/shoppingCart")
	public ModelAndView addToCart(
			@RequestParam(value="id", required=true) final String id, 
				@RequestParam(value="amount", required=true) final String amount) {
			
		return performCrudOperation(id,amount, (idNumber,productAmount,product) -> {
			for (int i = 0; i < productAmount; i++) {
				cart.addToCart(product);
			}
		});
	}
	
	@PostMapping(value="/shoppingCart/update")
	public ModelAndView updateCart(
			@RequestParam(value="id", required=true) final String id, 
				@RequestParam(value="amount", required=true) final String amount) {
		
		return performCrudOperation(id,amount, (idNumber,productAmount,product) -> cart.update(product,productAmount));
	}
	
	@PostMapping(value="/shoppingCart/remove")
	public ModelAndView removeProductFromCart(
			@RequestParam(value="id", required=true) final String id) {
		
		return performCrudOperation(id, (idNumber,productAmount,product) -> cart.update(product,ZERO));
	}
	
	private ModelAndView performCrudOperation(final String id, final TriConsumer operation) {
		return performCrudOperation(id, null, operation);
	}
	
	private ModelAndView performCrudOperation(final String id, final String amount, final TriConsumer operation) {
			
		final int productId = Integer.parseInt(id);
		final int productAmount = 
				(amount == null) ? ZERO : Integer.parseInt(amount);
		
		try {
			final Product product = productService.findById(productId);
			operation.accept(productId, productAmount, product);
		} catch (ProductNotFoundException e) {
			return Redirect.error();
		}
		return Redirect.success();
	}
	
	@PostMapping(value="/shoppingCart/submitOrder")
	public ModelAndView orderMapping(final HttpServletRequest request) {
		
		try {
			final int customerId = loggedInUserId(request);
			final int orderId = submitOrder(customerId);
			List<Orderline> orderLines = orderService.findMatchingOrderlines(orderId); // duplicate code from orderDetails (adminController)
			return new ModelAndView("orderdetails","model",orderLines);
		} catch (IllegalStateException ex) {
			return Redirect.error();
		}
	}
	
	private int loggedInUserId(final HttpServletRequest request) {
		final String username = request.getUserPrincipal().getName();
		System.out.println(username);
		int userId = userService.findUserId(username);
		System.out.println("User id: " + userId);
		
		return userId;
	}
	
	private int submitOrder(final int customerId) { // TODO: OptionalInt?
		
		Set<Product> uniqueItems = cart.uniqueItems();
		if (uniqueItems.isEmpty()) {
			throw new IllegalStateException("Cart is empty. Cannot create order");
		}
		
		final int orderId = orderService.newOrder(customerId);
		Map<Product,Integer> shoppingList = createShoppingList();
		
		for (Product product : shoppingList.keySet()) {
			orderService.createLine(orderId, product, shoppingList.get(product));
		}
		cart.clear();
		return orderId;
	}
	
	private Map<Product,Integer> createShoppingList() {
		
		final Set<Product> productsInCart = cart.uniqueItems();
		final Map<Product,Integer> shoppingList = new LinkedHashMap<>();
		
		for (Product product : productsInCart) {
			final int amount = cart.frequency(product);
			shoppingList.put(product,amount);
		}
		return Collections.unmodifiableMap(shoppingList);
	}
	
	@FunctionalInterface
	private static interface TriConsumer {
		void accept(int id, int amount, Product product) throws ProductNotFoundException;
	}

}
