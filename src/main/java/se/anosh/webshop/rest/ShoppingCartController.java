package se.anosh.webshop.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@RequestMapping(value="/shoppingCart", method=RequestMethod.GET) // TODO change to POST for production
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
	
	@RequestMapping(value="/shoppingCart/contents", method=RequestMethod.GET)
	public ModelAndView displayShoppingCart() {
		
		final Map<String,Object> contents = new HashMap<>();
		final List<Product> allProducts = new ArrayList<>(cart.allProducts());
		allProducts.sort(null);
		
		contents.put("products",allProducts);
		
		return new ModelAndView("cart","model",contents);
		
		
	}
//	
//	void addToCart(Product p);
//	int frequency(Product product);
//	Set<Product> uniqueItems();
//	int uniqueItemCount();
//	BigDecimal calculateTotalPrice();
//	default double calculateTotalPriceAsDouble() { return calculateTotalPrice().doubleValue(); }

}
