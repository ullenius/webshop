package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.domain.Order;

public interface OrderService {
	
	public List<Order> findAllOrders();
	public Order findById(int id);
	public void newOrder(Order newOrder);
	public void removeOrder(Order order);
	public void dispatchOrder();

}
