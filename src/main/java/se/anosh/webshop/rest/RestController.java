package se.anosh.webshop.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.service.OrderService;

@Controller
public class RestController {
	
	@Autowired
	private OrderService service;
	
	
	@RequestMapping(value="/api", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> listOrders() throws OrderNotFoundException {
		return new ResponseEntity<List<Order>>(service.findAllOrders(), HttpStatus.OK);
	}

}
