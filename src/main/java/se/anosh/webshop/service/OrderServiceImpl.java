package se.anosh.webshop.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.OrderDao;
import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao dao;

	@Override
	public List<Order> findAllOrders() {
		return dao.findAll();
	}
	
	@Override
	public List<Order> findAllUndispatchedOrders() {
		
		List<Order> allOrders = findAllOrders();
		List<Order> undispatched = new LinkedList<>();
		for (Order order : allOrders) {
			if (order.getDatum() == null)
				undispatched.add(order);
		}
		return undispatched;
	}

	@Override
	public Order findById(int id) throws OrderNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void newOrder(Order newOrder) {
		dao.add(newOrder);
	}

	@Override
	public void removeOrder(Order order) {
		dao.remove(order);
	}

	@Override
	public void dispatchOrder(Integer id) throws OrderNotFoundException {
		Order order = findById(id);
		
		if (order.getDatum() != null)
			throw new IllegalStateException("Order has already been dispatched");
		
		order.setDatum(new Date());
		dao.update(order);
	}

}
