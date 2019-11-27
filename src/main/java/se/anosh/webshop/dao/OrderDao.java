package se.anosh.webshop.dao;

import java.util.List;

import se.anosh.webshop.domain.Order;

public interface OrderDao {
	
	public List<Order> findAll();
	public Order findById(int id) throws OrderNotFoundException;
	public void add(Order item);
	public void remove(Order item);
	public void update(Order item);

}
