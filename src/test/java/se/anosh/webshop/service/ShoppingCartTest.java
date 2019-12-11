package se.anosh.webshop.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.anosh.webshop.domain.Product;

class ShoppingCartTest {
	
	private Shopping cart;
	private Product orange;
	private Product apple;
	
	private static final int NUMBER_OF_PIZZAS = 777;
	private static final int NUMBER_OF_PRODUCTS = 10;
	private static final int ONE = 1;
	private static final int TWO = 2;

	@BeforeEach
	void setUp() {
		
		cart = new ShoppingCart();
		
		orange = new Product();
		orange.setName("Orange");
		orange.setPrice(new BigDecimal("19.90"));
		
		apple = new Product();
		apple.setName("Apple");
		apple.setPrice(new BigDecimal("15.49"));
	}
	
	@AfterEach
	void after() {
		cart.clear();
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
	
	
	// Tests frequency() and uniqueItemCount
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
		assertEquals(ONE, cart.frequency(coconut));
		assertEquals(TWO, cart.uniqueItemCount());
	}
	
	@Test
	public void testUpdateProduct() {
		
		Product pizza = new Product();
		pizza.setName("Pizza");
		pizza.setPrice(new BigDecimal("95"));
		
		cart.addToCart(pizza);
		cart.update(pizza, NUMBER_OF_PIZZAS);
		assertEquals(NUMBER_OF_PIZZAS, cart.frequency(pizza));
		
		// reduce the number and test again
		cart.update(pizza, TWO);
		assertEquals(TWO, cart.frequency(pizza));
	}
	
	@Test
	public void testPriceCalculation() {
		
		final BigDecimal priceOfTenApples = new BigDecimal("154.90");
		final BigDecimal priceOfTenOranges = new BigDecimal("199");
		
		final BigDecimal total = priceOfTenApples.add(priceOfTenOranges);

		addFakeProducts();
		assertEquals(total, cart.calculateTotalPrice());
		
		final double totalDouble = total.doubleValue();
		assertEquals(totalDouble, cart.calculateTotalPriceAsDouble());
	}
	
	
	@Test
	public void testUniqueItemsSet() {
		addFakeProducts();
		final Set<Product> myProducts = cart.uniqueItems();
		
		assertTrue(myProducts.contains(apple));
		assertTrue(myProducts.contains(orange));
	}
	
	private void addFakeProducts() {
		
		for (int i = 0; i < 10; i++) {
			cart.addToCart(orange);
			cart.addToCart(apple);
		}
	}

}
