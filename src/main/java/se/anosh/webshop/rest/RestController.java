package se.anosh.webshop.rest;


import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.service.OrderService;

@Controller
public class RestController {

	@Autowired
	private OrderService service;


	@RequestMapping(value="/api", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listOrders() {
		return new ResponseEntity<List<Order>>(service.findAllOrders(), HttpStatus.OK);
	}

	@RequestMapping(value="/admin", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listUndispatchedOrders() {
		
		return new ResponseEntity<List<Order>>(service.findAllUndispatchedOrders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/{id}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ErrorMessage> dispatchOrder(@PathVariable("id") @NonNull String id) {
		
		try {
		Integer orderId = Integer.parseInt(id);
		service.dispatchOrder(orderId);
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
			this.message = message;
		}
	}


}
