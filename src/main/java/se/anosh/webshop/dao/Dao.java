package se.anosh.webshop.dao;

import java.util.List;

public interface Dao<T,S> {
	
	public List<T> findAll();
	public T findById(S id) throws Exception;
	public void add(T item);
	public void remove(T item);
	public void update(T item);

}
