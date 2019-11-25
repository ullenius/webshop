package se.anosh.webshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
		try {
			Order result = em.createQuery("SELECT order FROM Order as order WHERE order.id = :id", Order.class)
					.setParameter("id",id)
					.getSingleResult();
			return result;
		} catch (NoResultException e) {
			throw new OrderNotFoundException("Order with id: " + id + " not found");
		}
	}

	@Override
	public void add(Order item) {
		em.persist(item);
	}

	@Override
	public void remove(Order item) {
		em.remove(item);
	}

	@Override
	public void update(Order item) {
		em.merge(item);
	}

}
