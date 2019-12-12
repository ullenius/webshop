package se.anosh.webshop.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

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
import se.anosh.webshop.service.CategoryService;
import se.anosh.webshop.service.OrderService;
import se.anosh.webshop.service.ProductService;

@Controller
@SessionScope
public class AdminController {

	private OrderService orderService;
	private ProductService productService;
	private CategoryService categoryService;

	@Autowired
	public AdminController(OrderService orderService, ProductService productService, CategoryService categoryService) {
		this.orderService = Objects.requireNonNull(orderService);
		this.productService = Objects.requireNonNull(productService);
		this.categoryService = Objects.requireNonNull(categoryService);
	}

	@GetMapping(value="/admin")
	public ModelAndView showAllOrders() {

		List<Order> dispatchedOrders = orderService.findAllDispatchedOrders();
		List<Order> undispatchedOrders = orderService.findAllUndispatchedOrders();

		Map<String,Object> model = new HashMap<>();
		model.put("dispatched", dispatchedOrders);
		model.put("undispatched", undispatchedOrders);

		return new ModelAndView("admin", "model", model);
	}
	
	@GetMapping(value="/admin/order")
	public ModelAndView orderDetails(@RequestParam(value="id", required=true)final String id) {

		final int orderId = Integer.parseInt(id);
		List<Orderline> orderLines = orderService.findMatchingOrderlines(orderId);
		return new ModelAndView("orderdetails", "model", orderLines);
	}

	@GetMapping(value="/admin/addProduct")
	public ModelAndView addProduct() { //@NotNull @NonEmpty

		final List<Category> categories = categoryService.findAll();
		return new ModelAndView("addproduct", "addProductModel", new AddProductModel(categories));
	}

	// called by addProduct template
	@PostMapping(value="/admin/saveProduct")
	public ModelAndView saveUser(@Valid AddProductModel model) {

		Product product = new Product();
		product.setName(model.getName());
		product.setPrice(new BigDecimal(model.getPrice()));

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
	
	@PostMapping(value="/admin/dispatchOrder")
	public ModelAndView dispatchOrder(@RequestParam("orderId") String id) { //@NotNull @NonEmpty

		try {
			Integer orderId = Integer.parseInt(id);
			orderService.dispatchOrder(orderId);
			return new ModelAndView("admin/thankyou", "model", id);
		} catch (Exception ex) {
			return Redirect.error();
		}
	}

}
