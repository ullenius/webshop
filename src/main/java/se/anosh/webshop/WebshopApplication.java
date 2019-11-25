package se.anosh.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.anosh.webshop.service.OrderService;

@SpringBootApplication
public class WebshopApplication {
	
	@Autowired
	private OrderService service;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebshopApplication.class, args);
	}

}
