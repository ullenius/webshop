package se.anosh.webshop.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import se.anosh.webshop.domain.Product;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ShoppingCart implements Shopping {
	
	private final List<Product> shoppingCart; // TODO: make thread safe ConcurrentLinkedQueue or LinkedBlockingDeque
	
	public ShoppingCart() {
		shoppingCart = new LinkedList<>();
	}
	
	public void addToCart(Product p) {
		shoppingCart.add(p);
	}
	
	public int frequency(Product product) {
		return Collections.frequency(shoppingCart, product);
	}
	
	public Set<Product> uniqueItems() {
		return Collections.unmodifiableSet(new HashSet<>(shoppingCart));
	}
	
	public int uniqueItemCount() {
		Set<Product> uniqueItems = uniqueItems();
		return uniqueItems.size();
	}
	
	public BigDecimal calculateTotalPrice() {
		
		final Set<Product> uniqueItems = uniqueItems();
		BigDecimal sum = BigDecimal.valueOf(0);
		
		for (Product product : uniqueItems) {
			final BigDecimal multiplier = new BigDecimal(frequency(product));
			BigDecimal total = product.getPrice().multiply(multiplier);
			sum = sum.add(total);
		}
		
		return sum;
	}
	
	public String toString() {
		return shoppingCart.toString();
	}
	

}
