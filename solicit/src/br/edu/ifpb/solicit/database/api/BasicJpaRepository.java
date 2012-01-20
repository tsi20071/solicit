package br.edu.ifpb.solicit.database.api;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface BasicJpaRepository extends Serializable {
	void persist(Object entity);
	void remove(Object entity);
	void update(Object entity);
	long count(Class<?> classType);
	<T> T find(Class<T> classType, Object id);
	List findAll(Class<?> classType);
	List findAllReverse(Class<?> classType);
	<T> T merge(T entity) throws DataAccessException;
	List queryFind(String queryString);
	List queryFind(String queryString, Object ... values);
}
