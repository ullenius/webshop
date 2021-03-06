package se.anosh.webshop.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.api.CategoryService;
import se.anosh.webshop.service.api.ProductService;
import se.anosh.webshop.util.Logger;

@Controller
@SessionScope
public class ShopController {

	private final ProductService productService;
	private final CategoryService categoryService;

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
	
	@GetMapping(value="/")
	public ModelAndView mainPage() {
		return search("");
	}
	
	@GetMapping(value="/shop")
	public ModelAndView search(@RequestParam(value="products", required=false)final String products) {

		final Map<String,Object> model = new LinkedHashMap<>();
		
		final List<Product> matchingProducts = 
				(products == null) 
						? Collections.emptyList()
						: productService.findByName(products);

		model.put("products", matchingProducts);
		model.put("categories",findAllCategories());

		return new ModelAndView("main", "model", model);
	}

	@GetMapping(value="/shop/{id}")
	public ModelAndView searchByCategory(@PathVariable("id") final String id) {

		if (id.isEmpty())
			return Redirect.error();

		final Map<String,Object> model = new LinkedHashMap<>();
		final List<Product> matchingProducts;
		final int categoryId;
		try {
			categoryId = Integer.parseInt(id);
			Category category = categoryService.findById(categoryId);
			matchingProducts = productService.findByCategory(category);
		} catch (NumberFormatException | CategoryNotFoundException ex) {
			return Redirect.error();
		}

		model.put("categories",findAllCategories());
		model.put("products", matchingProducts);
		model.put("urlId", categoryId);

		return new ModelAndView("main", "model", model);
	}

	@GetMapping(value="/shop/allproducts")
	public ModelAndView listAllProducts() {

		final Map<String,Object> model = new LinkedHashMap<>();
		final List<Product> matchingProducts = productService.findAllProducts();

		model.put("categories",findAllCategories());
		model.put("products", matchingProducts);

		return new ModelAndView("main", "model", model);
	}

	private List<Category> findAllCategories() {
		return categoryService.findAll();
	}

}
