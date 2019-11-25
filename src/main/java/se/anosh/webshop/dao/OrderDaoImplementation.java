package se.anosh.webshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import se.anosh.webshop.domain.Order;
import se.anosh.webshop.service.OrderNotFoundException;

public class OrderDaoImplementation implements OrderDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Order> findAll() {
		return em.createQuery("select order from Order as order", Order.class).getResultList();
	}

	@Override
	public Order findById(int id) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Order item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Order item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Order item) {
		// TODO Auto-generated method stub
		
	}

}
