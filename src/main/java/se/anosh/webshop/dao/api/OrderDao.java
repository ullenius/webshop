package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Product;

public interface OrderDao {
	
	public List<Order> findAll();
	public Order findById(int id) throws OrderNotFoundException;
	public int add(int customerId);
	public void update(Order item);
	public void createLine(int orderId, Product product, int amount);

}
