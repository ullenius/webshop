package se.anosh.webshop.rest;


import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.dao.exception.OrderNotFoundException;
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

	@RequestMapping(value="/api", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listOrders() {
		return new ResponseEntity<List<Order>>(orderService.findAllOrders(), HttpStatus.OK);
	}

	@RequestMapping(value="/admin")
	public ModelAndView showAllOrders() {

		List<Order> dispatchedOrders = orderService.findAllDispatchedOrders();
		List<Order> undispatchedOrders = orderService.findAllUndispatchedOrders();

		Map<String,Object> model = new HashMap<>();
		model.put("dispatched", dispatchedOrders);
		model.put("undispatched", undispatchedOrders);

		return new ModelAndView("admin", "model", model);
	}

	// /admin/addProduct
	@RequestMapping(value="/admin/addProduct", method=RequestMethod.GET)
	public ModelAndView addProduct() { //@NotNull @NonEmpty

		final List<Category> categories = categoryService.findAll();
		return new ModelAndView("addproduct", "addProductModel", new AddProductModel(categories));
	}
	
	// called by addProduct template
	@RequestMapping(value="/admin/saveProduct", method=RequestMethod.POST)
	public ModelAndView saveUser(@Valid AddProductModel model) {
		
		Product product = new Product();
		product.setName(model.getName());
		product.setPrice(new BigDecimal(model.getPrice()));
		
		try {
			final int id = Integer.parseInt(model.getCategory());
			Category category = categoryService.findById(id);
			product.setCategory(category);
			
			System.out.println("Adding: " + product); // psuedo
			
			
		} catch (CategoryNotFoundException | NumberFormatException ex) {
			return new ModelAndView("redirect:/error.html");
		}
		return new ModelAndView("redirect:/success.html");
	}

	// JSON method
	@RequestMapping(value="/admin/addProduct", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addProductJson(@NotNull @Valid @RequestBody Product newProduct) {
		System.out.println("Received: " + newProduct);
		productService.addProduct(newProduct);
		return ResponseEntity.accepted().build();
	}

	@RequestMapping(value="/admin/order")
	public ModelAndView orderDetails(@RequestParam(value="id", required=true)final String id) {

		final int orderId = Integer.parseInt(id);
		System.out.println("Parsed order id = " + orderId);

		List<Orderline> orderLines = orderService.findMatchingOrderlines(orderId);
		return new ModelAndView("orderdetails", "model", orderLines);
	}

	@RequestMapping(value="/admin/undispatched", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listUndispatchedOrders() {

		return new ResponseEntity<List<Order>>(orderService.findAllUndispatchedOrders(), HttpStatus.OK);
	}

	@RequestMapping(value="/admin/dispatched", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listDispatchedOrders() {

		return new ResponseEntity<List<Order>>(orderService.findAllDispatchedOrders(), HttpStatus.OK);
	}

	@RequestMapping(value="/admin/dispatchOrder", method=RequestMethod.POST)
	public ModelAndView dispatchOrder(@RequestParam("orderId") String id) { //@NotNull @NonEmpty

		try {
			Integer orderId = Integer.parseInt(id);
			System.out.println("Argument passed in: " + id);
			orderService.dispatchOrder(orderId);
			return new ModelAndView("admin/thankyou", "model", id);
		} catch (Exception ex) {
			return new ModelAndView("redirect:/error.html");
		}
	}
	

	// JSON version of above method
	@RequestMapping(value="/admin/{orderId}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ErrorMessage> dispatchOrderJson(@PathVariable("orderId") String id) {
		try {
			int orderId = Integer.parseInt(id);
			System.out.println("Argument passed in (JSON version): " + id);
			orderService.dispatchOrder(orderId);
			return new ResponseEntity<ErrorMessage>(new ErrorMessage(null), HttpStatus.ACCEPTED);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("Order with id " + id + " not found"), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException ex) {
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("Bad input"), HttpStatus.BAD_REQUEST);
		}
	}

	// demo method
	@RequestMapping(value="/products", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> listProducts() {
		return new ResponseEntity<List<Product>>(productService.findAllProducts(), HttpStatus.OK);
	}

	@RequestMapping(value="/shop")
	public ModelAndView search(@RequestParam(value="products", required=false)final String products) {

		final Map<String,Object> model = new LinkedHashMap<>();

		System.out.println("products = " + products);
		List<Product> matchingProducts;
		matchingProducts = (products == null) 
				? Collections.emptyList()
						: productService.findByName(products);

				System.out.println("Products = " + matchingProducts);

				List<Category> categories = categoryService.findAll();
				System.out.println("Size of categories = " + categories.size());
				model.put("categories",categories);
				model.put("products", matchingProducts);

				return new ModelAndView("main", "model", model);
	}

	@XmlRootElement
	private class ErrorMessage {
		@XmlElement(name="error")
		private final String message;
		ErrorMessage(String message) {
			this.message = message; //null is allowed, JSON ignores it
		}
	}


}
