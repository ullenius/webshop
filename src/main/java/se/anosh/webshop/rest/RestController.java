package se.anosh.webshop.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.anosh.webshop.domain.Order;
import se.anosh.webshop.service.OrderNotFoundException;
import se.anosh.webshop.service.OrderService;

@Controller
public class RestController {
	
	@Autowired
	private OrderService service;
	
	
	@RequestMapping(value="/api", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> listOrders() throws OrderNotFoundException {
		return new ResponseEntity<Order>(service.findById(0), HttpStatus.OK);
	}

}
