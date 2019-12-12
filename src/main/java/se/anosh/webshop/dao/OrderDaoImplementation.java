package se.anosh.webshop.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import se.anosh.webshop.dao.api.OrderDao;
import se.anosh.webshop.dao.exception.OrderNotFoundException;
import se.anosh.webshop.domain.Order;
import se.anosh.webshop.domain.Product;

@Repository
public class OrderDaoImplementation implements OrderDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Order> findAll() {
		List<Order> allOrders = em.createQuery("SELECT order FROM Order as order", Order.class).getResultList();
		return Collections.unmodifiableList(allOrders);
	}

	@Override
	public Order findById(final int id) throws OrderNotFoundException {
		try {
			TypedQuery<Order> query = em.createQuery("SELECT order FROM Order as order WHERE order.id = :id", Order.class);
			query.setParameter("id",id);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new OrderNotFoundException("Order with id: " + id + " not found");
		}
	}

	@Override
	public int add(final int customerId) {
		
		Query query = em.createNativeQuery("INSERT INTO orders (customer) VALUES (:id)");
		query.setParameter("id", customerId);
		int result = query.executeUpdate();
		if (result == 0) 
			throw new IllegalArgumentException("Customer with id: " + customerId + " not found");
		
		Query idQuery = em.createNativeQuery("SELECT id FROM orders WHERE customer = :id");
		idQuery.setParameter("id", customerId);
		int orderId = ((Number) idQuery.getSingleResult()).intValue();
		return orderId;
	}

	@Override
	public void update(Order item) {
		em.merge(item);
	}

	@Override
	public void createLine(final int orderId, final Product product, final int amount) {
		
		final Query query = em.createNativeQuery(
				"INSERT INTO orderlines (orderlines.order, product, quantity) "
				+ "VALUES (:order, :product, :quantity)");
		query.setParameter("order", orderId);
		query.setParameter("product", product.getId());
		query.setParameter("quantity", amount);
		
		query.executeUpdate();
	}

}
