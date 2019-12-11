package se.anosh.webshop.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.ProductService;
import se.anosh.webshop.service.Shopping;
import se.anosh.webshop.service.ShoppingCart;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCartController {
	
	private Shopping cart;
	private ProductService productService;
	private static final int ZERO = 0;
	
	@Autowired
	public ShoppingCartController(ProductService productService) {
		this.productService = productService;
		cart = new ShoppingCart();
	}
	
	@RequestMapping(value="/shoppingCart", method=RequestMethod.GET)
	public ModelAndView displayShoppingCart() {
		
		final Map<String,Object> contents = new HashMap<>();
		final Set<Product> productsInCart = cart.uniqueItems();
		
		final Map<Product,Integer> shoppingCartView = new LinkedHashMap<>();
		for (Product product : productsInCart) {
			final int amount = cart.frequency(product);
			shoppingCartView.put(product,amount);
		}
		contents.put("products",shoppingCartView);
		contents.put("totalPrice",cart.calculateTotalPrice());
		
		return new ModelAndView("cart","model",contents);
	}
	
	@RequestMapping(value="/shoppingCart", method=RequestMethod.POST)
	public ModelAndView addToCart(
			@RequestParam(value="id", required=true) String id, 
				@RequestParam(value="amount", required=true) String amount) {
			
		return performCrudOperation(id,amount, (idNumber,productAmount,product) -> {
			for (int i = 0; i < productAmount; i++) {
				cart.addToCart(product);
			}
		});
	}
	
	@RequestMapping(value="/shoppingCart/update", method=RequestMethod.GET)
	public ModelAndView updateCart(
			@RequestParam(value="id", required=true) String id, 
				@RequestParam(value="amount", required=true) String amount) {
		
		return performCrudOperation(id,amount, (idNumber,productAmount,product) -> cart.update(product,productAmount));
	}
	
	@RequestMapping(value="/shoppingCart/remove", method=RequestMethod.GET)
	public ModelAndView removeProductFromCart(
			@RequestParam(value="id", required=true) String id) {
		
		return performCrudOperation(id, (idNumber,productAmount,product) -> cart.update(product,ZERO));
	}
	
	private ModelAndView performCrudOperation(final String id, final TriConsumer operation) {
		return performCrudOperation(id, null, operation);
	}
	
	private ModelAndView performCrudOperation(String id, String amount, TriConsumer operation) {
			
		final int productId = Integer.parseInt(id);
		final int productAmount = Integer.parseInt(amount);
		
		final Product product;
		try {
			product = productService.findById(productId);
			operation.accept(productId, productAmount, product);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/error.html");
		}
		
		System.out.println("UPDATED - Contents of cart: " + cart);
		return new ModelAndView("redirect:/success.html");
	}
	
	
	@FunctionalInterface
	private static interface TriConsumer {
		void accept(int id, int amount, Product product) throws ProductNotFoundException;
	}

}
