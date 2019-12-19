package se.anosh.webshop.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.model.AddUserModel;
import se.anosh.webshop.service.CategoryService;
import se.anosh.webshop.service.ProductService;

@Controller
@SessionScope
public class ShopController {
	
	private ProductService productService;
	private CategoryService categoryService;

	@Autowired
	public ShopController(ProductService productService, CategoryService categoryService) {
		this.productService = Objects.requireNonNull(productService);
		this.categoryService = Objects.requireNonNull(categoryService);
	}
	
	@GetMapping(value="/addUser")
	public ModelAndView newUser() {
		return new ModelAndView("create-account", "addUserModel", new AddUserModel());
	}
	
	@PostMapping(value="/saveUser")
	public ModelAndView saveUser(@Valid AddUserModel addUserModel, Errors results) {
		
		if (results.hasErrors()) {
			return new ModelAndView("create-account", "addUserModel", addUserModel);
		}
		
		System.out.println("Success! Received: " + addUserModel);
		
		return Redirect.success();
	}
	
	//public ModelAndView processForm(@Valid final UserFormObject newUser, final Errors results) {
	//
//		if (results.hasErrors()) {
//			return new ModelAndView("create-account", "userFormObject" , newUser);
//		}
	//
//		List<GrantedAuthority> roles = new ArrayList<>();
//		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
	//
//		String encodedPassword = encoder.encode(newUser.getPassword());
	//	
//		User user = new User(newUser.getUsername(), encodedPassword, roles);
	//
//		try {
//			userManager.createUser(user);
//		} catch (DuplicateKeyException ex) {
//			results.rejectValue("username", "username.unique");
//			newUser.setPassword(null); // just to be on the safe side
//			return new ModelAndView("create-account", "userFormObject", newUser);
//		}
	//
//		Authentication credentials = new UsernamePasswordAuthenticationToken(user.getUsername(), newUser.getPassword());
//		credentials = authenticationManager.authenticate(credentials);
//		if (credentials.isAuthenticated()) {
//			// GOOD!
//			SecurityContextHolder.getContext().setAuthentication(credentials);
//			return new ModelAndView("redirect:/viewAllBooks.do");
//		} else {
//			throw new RuntimeException("For some reason, the user didn't log in even though it should have been automatic");
//		}
	//
	//}

	
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
	public ModelAndView search(@RequestParam(value="products", required=false)final String products, HttpServletResponse response) {

		final Map<String,Object> model = new LinkedHashMap<>();

		final List<Product> matchingProducts = 
				(products == null) 
					? Collections.emptyList()
					: productService.findByName(products);

		model.put("categories",findAllCategories());
		model.put("products", matchingProducts);

		response.setStatus(HttpStatus.I_AM_A_TEAPOT.value()); // TODO: test this!
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
			Category criteria = categoryService.findById(categoryId);
			matchingProducts = productService.findByCategory(criteria);
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
