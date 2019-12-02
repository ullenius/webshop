package se.anosh.webshop;

import java.util.Date;

import se.anosh.webshop.domain.Order;

public class PojoDemo {
	
	public static void main(String[] args) {
		
		Order order = new Order(new Date());
		Order order2 = new Order(new Date());
		
		System.out.println("first: " + order + " nanoseconds: " + order.getNanoStamp());
		System.out.println("second: " + order2 + " nanoseconds: " + order.getNanoStamp());
		
	}

}
