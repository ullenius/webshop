package se.anosh.webshop.service;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.OrderDaoImplementation;
import se.anosh.webshop.dao.api.OrderDao;
import se.anosh.webshop.dao.api.OrderLineDao;
import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Orderline;
import se.anosh.webshop.domain.Product;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao dao;
	
	@Autowired
	private OrderLineDao orderLineDao;
	
	@Override
	public List<Orderline> findMatchingOrderlines(int orderId) {
		return orderLineDao.findMatchingOrderlines(orderId);
	}
	
	@Override
	public List<Order> findAllOrders() {
		return dao.findAll();
	}
	
	@Override
	public List<Order> findAllUndispatchedOrders() {
		return findMatchingOrders( order -> order.getDatum() == null);
	}

	@Override
	public List<Order> findAllDispatchedOrders() {
		return findMatchingOrders( order -> order.getDatum() != null);
	}
	
	private List<Order> findMatchingOrders(Predicate<Order> criteria) {
		List<Order> allOrders = findAllOrders();
		List<Order> matching = new LinkedList<>();
		for (Order order : allOrders) {
			if (criteria.test(order))
				matching.add(order);
		}
		return Collections.unmodifiableList(matching);
	}

	@Override
	public Order findById(int id) throws OrderNotFoundException {
		return dao.findById(id);
	}

	@Override
	public int newOrder(int customerId) {
		return dao.add(customerId);
	}

	@Override
	public void removeOrder(Order order) {
		dao.remove(order);
	}

	@Override
	public void dispatchOrder(Integer id) throws OrderNotFoundException {
		final Order order = findById(id);
		
		if (order.getDatum() != null)
			throw new IllegalStateException("Order has already been dispatched");
		order.setDate(new Date());
		dao.update(order);
	}

	@Override
	public void createLine(int orderId, Product product, int amount) {
		dao.createLine(orderId, product, amount);
	}

}
