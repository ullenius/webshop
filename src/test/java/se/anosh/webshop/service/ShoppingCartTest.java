package se.anosh.webshop.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.anosh.webshop.domain.Product;

class ShoppingCartTest {
	
	private Shopping cart;
	private static final int NUMBER_OF_PRODUCTS = 10;
	private static final int ONE = 1;

	@BeforeEach
	void setUp() throws Exception {
		
		cart = new ShoppingCart();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void testThatCartIsEmpty() {
		List<Product> contents = cart.allProducts();
		assertTrue(contents.isEmpty());
	}
	
	@Test
	void testAddToCart() {
		cart.addToCart(new Product());
		List<Product> contents = cart.allProducts();
		assertFalse(contents.isEmpty());
	}
	
	@Test
	void testClearCart() {
		cart.addToCart(new Product());
		cart.clear();
		List<Product> contents = cart.allProducts();
		assertTrue(contents.isEmpty());
	}
	
	@Test
	void testProductCounter() {
		
		Product beer = new Product();
		beer.setName("Beer");
		beer.setPrice(new BigDecimal("39.90"));
		
		Product coconut = new Product();
		coconut.setName("Coconut");
		coconut.setPrice(new BigDecimal("99.90"));
		
		cart.addToCart(coconut);
		
		for (int i = 0; i < NUMBER_OF_PRODUCTS; i++) {
			cart.addToCart(beer);
		}
		assertEquals(NUMBER_OF_PRODUCTS, cart.frequency(beer));
		assertEquals(ONE,cart.frequency(coconut));
	}
	
	@Test
	
	
	
	// Adds 2 different products, 10 each (20 in total)
	private void addFakeProducts() {
		
		Product orange = new Product();
		orange.setName("Orange");
		orange.setPrice(new BigDecimal("19.90"));
		
		Product apple = new Product();
		apple.setName("Apple");
		apple.setPrice(new BigDecimal("15.49"));
		
		for (int i = 0; i < 10; i++) {
			cart.addToCart(orange);
			cart.addToCart(apple);
		}
	}
	
	
	
//	void addToCart(Product p);
//	int frequency(Product product);
//	void update(final Product product, final int amount);
//	Set<Product> uniqueItems();
//	int uniqueItemCount();
//	List<Product> allProducts();
//	BigDecimal calculateTotalPrice();
//	default double calculateTotalPriceAsDouble() { return calculateTotalPrice().doubleValue(); }
//	void clear();


}
