package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;

public interface OrderDao {
	
	public List<Order> findAll();
	public Order findById(int id) throws OrderNotFoundException;
	public int add(int customerId);
	public void remove(Order item);
	public void update(Order item);

}
