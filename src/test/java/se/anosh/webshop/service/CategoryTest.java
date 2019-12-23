package se.anosh.webshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.anosh.webshop.domain.Category;

class CategoryTest {
	
	private Category first;
	private Category second;

	@BeforeEach
	void setUp() {
		first = new Category();
		second = new Category();
	}

	@Test
	void testLowercaseName() {
		first.setName("Food");
		assertEquals("food",first.getName());
	}
	
	@Test
	void testDefaultEmptyName() {
		assertNotNull(second.getName());
		assertTrue(second.getName().isEmpty());
	}

}
