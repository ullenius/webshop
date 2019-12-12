package se.anosh.webshop.rest;

import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Orderline;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.CategoryService;
import se.anosh.webshop.service.OrderService;
import se.anosh.webshop.service.ProductService;

@Controller
@SessionScope
public class AdminRestController {

	private OrderService orderService;
	private ProductService productService;

	@Autowired
	public AdminRestController(OrderService orderService, ProductService productService, CategoryService categoryService) {
		this.orderService = Objects.requireNonNull(orderService);
		this.productService = Objects.requireNonNull(productService);
	}

	// JSON method
	@PostMapping(value="/admin/addProduct", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addProductJson(@NotNull @Valid @RequestBody Product newProduct) {
		productService.addProduct(newProduct);
		return ResponseEntity.accepted().build();
	}

	@RequestMapping(value="/admin/undispatched", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listUndispatchedOrders() {
		return new ResponseEntity<List<Order>>(orderService.findAllUndispatchedOrders(), HttpStatus.OK);
	}

	@RequestMapping(value="/admin/dispatched", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listDispatchedOrders() {
		return new ResponseEntity<List<Order>>(orderService.findAllDispatchedOrders(), HttpStatus.OK);
	}

	// JSON version of above method (/admin/dispatchOrder)
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

	@XmlRootElement
	private class ErrorMessage {
		@XmlElement(name="error")
		private final String message;
		ErrorMessage(String message) {
			this.message = message; //null is allowed, JSON ignores it
		}
	}

}
