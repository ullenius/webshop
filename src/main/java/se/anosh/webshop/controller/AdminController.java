package se.anosh.webshop.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Orderline;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.model.AddProductModel;
import se.anosh.webshop.service.api.CategoryService;
import se.anosh.webshop.service.api.OrderService;
import se.anosh.webshop.service.api.ProductService;
import se.anosh.webshop.util.Logger;

@Controller
@SessionScope
@RequestMapping("/admin")
public class AdminController {

	private final OrderService orderService;
	private final ProductService productService;
	private final CategoryService categoryService;

	@Autowired
	public AdminController(OrderService orderService, ProductService productService, CategoryService categoryService) {
		this.orderService = Objects.requireNonNull(orderService);
		this.productService = Objects.requireNonNull(productService);
		this.categoryService = Objects.requireNonNull(categoryService);
	}
	
	@GetMapping(value="/dispatchedOrders")
	public ModelAndView dispatchedOrders() {
		
		List<Order> dispatchedOrders = orderService.findAllDispatchedOrders();
		Map<String,Object> model = new HashMap<>();
		model.put("dispatched", dispatchedOrders);
		return new ModelAndView("admin", "model", model);
	}
	
	@GetMapping(value="/undispatchedOrders")
	public ModelAndView undispatchedOrders() {
		
		List<Order> undispatchedOrders = orderService.findAllUndispatchedOrders();
		Map<String,Object> model = new HashMap<>();
		model.put("undispatched", undispatchedOrders);
		
		return new ModelAndView("undispatchedOrders", "model", model);
	}
	
	@GetMapping(value="/order")
	public ModelAndView orderDetails(@RequestParam(value="id", required=true)final String id) {

		final int orderId = Integer.parseInt(id);
		List<Orderline> orderLines = orderService.findMatchingOrderlines(orderId);
		Logger.log("Orderlines = " + orderLines);
		
		return new ModelAndView("orderdetails", "model", orderLines);
	}

	@GetMapping(value="/addProduct")
	public ModelAndView addProduct() {

		final List<Category> categories = categoryService.findAll();
		return new ModelAndView("addproduct", "addProductModel", new AddProductModel(categories));
	}

	@PostMapping(value="/saveProduct")
	public ModelAndView saveUser(@Valid AddProductModel model) {

		final Product product = new Product();
		product.setName(model.getName());
		BigDecimal price = new BigDecimal(model.getPrice());
		product.setPrice(price);

		try {
			final int id = Integer.parseInt(model.getCategory());
			Category category = categoryService.findById(id);
			product.setCategory(category);
			productService.addProduct(product);

		} catch (CategoryNotFoundException | NumberFormatException ex) {
			return Redirect.error();
		}
		return Redirect.success();
	}
	
	@PostMapping(value="/dispatchOrder")
	public ModelAndView dispatchOrder(@RequestParam("orderId") @NotEmpty final String id) {

		try {
			Integer orderId = Integer.parseInt(id);
			orderService.dispatchOrder(orderId);
			return new ModelAndView("admin/thankyou", "model", id);
		} catch (Exception ex) {
			return Redirect.error();
		}
	}

}
