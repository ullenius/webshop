package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.Dao;
import se.anosh.webshop.domain.Order;

public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private Dao<Order,Integer> dao;

	@Override
	public List<Order> findAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findById(int id) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newOrder(Order newOrder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeOrder(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispatchOrder() {
		// TODO Auto-generated method stub

	}

}
