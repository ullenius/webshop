package se.anosh.webshop.rest;



import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.CategoryService;
import se.anosh.webshop.service.OrderService;
import se.anosh.webshop.service.ProductService;

@Controller
@SessionScope
public class RestController {

	private OrderService orderService;
	private ProductService productService;
	private CategoryService categoryService;

	@Autowired
	public RestController(OrderService orderService, ProductService productService, CategoryService categoryService) {
		this.orderService = Objects.requireNonNull(orderService);
		this.productService = Objects.requireNonNull(productService);
		this.categoryService = Objects.requireNonNull(categoryService);
	}

	@GetMapping(value="/api", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listOrders() {
		return new ResponseEntity<List<Order>>(orderService.findAllOrders(), HttpStatus.OK);
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

	@GetMapping(value="/products", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> listProducts() {
		return new ResponseEntity<List<Product>>(productService.findAllProducts(), HttpStatus.OK);
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
