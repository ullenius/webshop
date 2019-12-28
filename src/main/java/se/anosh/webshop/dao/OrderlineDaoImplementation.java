package se.anosh.webshop.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Controller;

import se.anosh.webshop.dao.api.OrderLineDao;
import se.anosh.webshop.domain.Orderline;

@Controller
public class OrderlineDaoImplementation implements OrderLineDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Orderline> findMatchingOrderlines(int orderid) {
		Query query = em.createNativeQuery(
				"SELECT id, quantity, orderlines.order, product FROM orderlines WHERE orderlines.order = ?", Orderline.class);
		query.setParameter(1, orderid);
		
		List<Orderline> orderlines = (List<Orderline>) query.getResultList();
		
		return Collections.unmodifiableList(orderlines);
	}

}
