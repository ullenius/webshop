package se.anosh.webshop.service;

import java.math.BigDecimal;
import java.util.Set;

import se.anosh.webshop.domain.Product;

public interface Shopping {
		
		void addToCart(Product p);
		int frequency(Product product);
		Set<Product> uniqueItems();
		int uniqueItemCount();
		BigDecimal calculateTotalPrice();
		default double calculateTotalPriceAsDouble() { return calculateTotalPrice().doubleValue(); }

}
