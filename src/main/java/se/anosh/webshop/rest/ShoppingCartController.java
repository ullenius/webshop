package se.anosh.webshop.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
	
	@Autowired
	public ShoppingCartController(ProductService productService) {
		this.productService = productService;
		cart = new ShoppingCart();
	}
	
	@RequestMapping(value="/shoppingCart", method=RequestMethod.POST)
	public ModelAndView addToCart(
			@RequestParam(value="id", required=true) String id, 
				@RequestParam(value="amount", required=true) String amount) {
			
		final int productId = Integer.parseInt(id);
		final int productAmount = Integer.parseInt(amount);
		
		final Product product;
		try {
			product = productService.findById(productId);
			for (int i = 0; i < productAmount; i++) {
				cart.addToCart(product);
			}
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Contents of cart: " + cart);
		return new ModelAndView("redirect:/success.html");
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
	
	@RequestMapping(value="/shoppingCart/update", method=RequestMethod.GET)
	public ModelAndView updateCart(
			@RequestParam(value="id", required=true) String id, 
				@RequestParam(value="amount", required=true) String amount) {
			
		final int productId = Integer.parseInt(id);
		final int productAmount = Integer.parseInt(amount);
		
		final Product product;
		try {
			product = productService.findById(productId);
			cart.update(product, productAmount);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/error.html");
		}
		
		System.out.println("UPDATED - Contents of cart: " + cart);
		return new ModelAndView("redirect:/success.html");
	}
	
	/// demo stuff
	public ModelAndView performCrudOperation(String id, String amount, ProductConsumer operation) {
			
		final int productId = Integer.parseInt(id);
		final int productAmount = Integer.parseInt(amount);
		
		final Product product = null;
		try {
			operation.accept(product);
//			product = productService.findById(productId);
//			cart.update(product, productAmount);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/error.html");
		}
		
		System.out.println("UPDATED - Contents of cart: " + cart);
		return new ModelAndView("redirect:/success.html");
	}
	
	@FunctionalInterface
	private static interface ProductConsumer {
		void accept(Product product) throws ProductNotFoundException;
	}
	

}
