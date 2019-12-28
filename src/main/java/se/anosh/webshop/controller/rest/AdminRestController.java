package se.anosh.webshop.controller.rest;

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
import org.springframework.web.context.annotation.SessionScope;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Product;
import se.anosh.webshop.service.OrderService;
import se.anosh.webshop.service.ProductService;

@Controller
@SessionScope
@RequestMapping("/admin")
public class AdminRestController {

	private final OrderService orderService;
	private final ProductService productService;

	@Autowired
	public AdminRestController(OrderService orderService, ProductService productService) {
		this.orderService = Objects.requireNonNull(orderService);
		this.productService = Objects.requireNonNull(productService);
	}

	// JSON method
	@PostMapping(value="/addProduct", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addProductJson(@NotNull @Valid @RequestBody Product newProduct) {
		productService.addProduct(newProduct);
		return ResponseEntity.accepted().build();
	}

	@GetMapping(value="/undispatched", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listUndispatchedOrders() {
		return new ResponseEntity<List<Order>>(orderService.findAllUndispatchedOrders(), HttpStatus.OK);
	}

	@GetMapping(value="/dispatched", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listDispatchedOrders() {
		return new ResponseEntity<List<Order>>(orderService.findAllDispatchedOrders(), HttpStatus.OK);
	}

	// JSON version of (/admin/dispatchOrder)
	@PostMapping(value="/{orderId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ErrorMessage> dispatchOrderJson(@PathVariable("orderId") String id) {
		try {
			int orderId = Integer.parseInt(id);
			orderService.dispatchOrder(orderId);
			return new ResponseEntity<ErrorMessage>(new ErrorMessage(null), HttpStatus.ACCEPTED);
		} catch (OrderNotFoundException e) {
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("Order with id " + id + " not found"), HttpStatus.NOT_FOUND);
		} catch (NumberFormatException ex) {
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("Bad input"), HttpStatus.BAD_REQUEST);
		}
	}

	@XmlRootElement
	private static class ErrorMessage {
		@XmlElement(name="error")
		private final String message;
		ErrorMessage(String message) {
			this.message = message; //null is allowed, JSON ignores it
		}
	}

}
