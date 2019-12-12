package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Orderline;
import se.anosh.webshop.domain.Product;

public interface OrderService {
	
	public List<Order> findAllOrders();
	public List<Order> findAllDispatchedOrders();
	public List<Order> findAllUndispatchedOrders();
	public Order findById(int id) throws OrderNotFoundException;
	public int newOrder(int customerId);
	public void removeOrder(Order order);
	public void dispatchOrder(Integer id) throws OrderNotFoundException;
	
	public List<Orderline> findMatchingOrderlines(int orderId);
	public void createLine(int orderId, Product product, int amount);
}
