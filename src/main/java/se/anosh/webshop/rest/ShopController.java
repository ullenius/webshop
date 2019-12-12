package se.anosh.webshop.rest;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.CategoryService;
import se.anosh.webshop.service.ProductService;

public class ShopController {
	
	private ProductService productService;
	private CategoryService categoryService;

	@Autowired
	public ShopController(ProductService productService, CategoryService categoryService) {
		this.productService = Objects.requireNonNull(productService);
		this.categoryService = Objects.requireNonNull(categoryService);
	}
	
	@GetMapping(value="/product/{productId}")
	public ModelAndView addProductToCart(@PathVariable("productId") final String id) {

		try {
			int productId = Integer.parseInt(id);
			Product product = productService.findById(productId);

			return new ModelAndView("product", "model", product);
		} catch (NumberFormatException | ProductNotFoundException ex) {
			return Redirect.error();
		}
	}
	
	@GetMapping(value="/shop")
	public ModelAndView search(@RequestParam(value="products", required=false)final String products) {

		final Map<String,Object> model = new LinkedHashMap<>();

		final List<Product> matchingProducts = 
				(products == null) 
					? Collections.emptyList()
					: productService.findByName(products);

		List<Category> categories = categoryService.findAll();
		model.put("categories",categories);
		model.put("products", matchingProducts);

		return new ModelAndView("main", "model", model);
	}
	

}
