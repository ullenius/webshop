package se.anosh.webshop.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import se.anosh.webshop.domain.Product;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCart {
	
	private final List<Product> shoppingCart;
	
	public ShoppingCart() {
		shoppingCart = new LinkedList<>();
	}
	
	void addToCart(Product p) {
		shoppingCart.add(p);
	}
	
	int frequency(Product product) {
		return Collections.frequency(shoppingCart, product);
	}
	
	int uniqueItemCount() {
		final Set<Product> uniqueItems = new HashSet<>(shoppingCart);
		return uniqueItems.size();
	}
	

}
