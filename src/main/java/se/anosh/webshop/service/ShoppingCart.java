package se.anosh.webshop.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
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

	public void update(final Product product, final int amount) {
		
		if (!shoppingCart.contains(product))
			return;

		final int frequency = frequency(product);
		if (amount > frequency) {
			add(product, amount-frequency);
			
		} else if (amount < frequency) {
			remove(product, frequency-amount);
		}
	}

	private void add(Product product, int amount) {
		for (int i = 0; i < amount; i++)
			shoppingCart.add(product);
	}

	private void remove(final Product product, final int amount) {

		if (shoppingCart.contains(product)) {
			for (int i = 0; i < amount; i++) {
				shoppingCart.remove(product);
			}
		}
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

	public List<Product> allProducts() {
		return Collections.unmodifiableList(shoppingCart);
	}
	
	public String toString() {
		return shoppingCart.toString();
	}

	@Override
	public void clear() {
		shoppingCart.clear();
	}


}
