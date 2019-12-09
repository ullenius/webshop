package se.anosh.webshop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import se.anosh.webshop.domain.Product;

// all return values are immutable and non-null
public interface Shopping {
		
		void addToCart(Product p);
		int frequency(Product product);
		Set<Product> uniqueItems();
		int uniqueItemCount();
		BigDecimal calculateTotalPrice();
		default double calculateTotalPriceAsDouble() { return calculateTotalPrice().doubleValue(); }
		List<Product> allProducts();

}
