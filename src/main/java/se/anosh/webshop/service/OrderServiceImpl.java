package se.anosh.webshop.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.OrderNotFoundException;
import se.anosh.webshop.dao.api.OrderDao;
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
	public void dispatchOrder(Order order) {
		order.setDatum(new Date());
		dao.update(order);
	}

}
