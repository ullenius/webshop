package se.anosh.webshop.service.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import se.anosh.webshop.domain.Product;

// all return values are immutable and non-null
public interface Shopping {
		
		void addToCart(Product p);
		
		int frequency(Product product);
		
		void update(final Product product, final int amount);
		
		Set<Product> uniqueItems();
		
		int uniqueItemCount();
		
		List<Product> allProducts();
		
		BigDecimal calculateTotalPrice();

		default double calculateTotalPriceAsDouble() { return calculateTotalPrice().doubleValue(); }
		
		void clear();

}
