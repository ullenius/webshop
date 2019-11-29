package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;

public interface OrderService {
	
	public List<Order> findAllOrders();
	public List<Order> findAllUndispatchedOrders();
	public Order findById(int id) throws OrderNotFoundException;
	public void newOrder(Order newOrder);
	public void removeOrder(Order order);
	public void dispatchOrder(Integer id) throws OrderNotFoundException;

}
