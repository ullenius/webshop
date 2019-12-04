package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.domain.Orderline;

@FunctionalInterface
public interface OrderLineDao {
	
	List<Orderline> findMatchingOrderlines(int orderid);
}
