package br.edu.ifpb.solicit.database.implementation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.solicit.database.api.BasicJpaRepository;

@Service("repositoryService")
@Transactional
public class BasicJpaRepositoryImpl implements BasicJpaRepository, Serializable {
	@PersistenceContext
	private EntityManager entityManager;

	public BasicJpaRepositoryImpl() {}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void persist(Object entity){
		this.entityManager.persist(entity);
	}

	@Override
	public void remove(Object entity) {
		this.entityManager.remove(entity);
	}

	@Override
	public void update(Object entity) {
		this.entityManager.merge(entity);
	}

	@Override
	public long count(Class<?> classType) {
		Query query = this.entityManager.createQuery("select count(x) from " + classType.getSimpleName() + " x");
		return ((Long) query.getResultList().get(0));
	}

	@Override
	public <T> T find(Class<T> classType, Object id) {
		return this.entityManager.find(classType, id);
	}

	@Override
	public List findAll(Class<?> classType) {
		return this.entityManager.createQuery("select x from " + classType.getSimpleName() + " x").getResultList();
	}

	@Override
	public List findAllReverse(Class<?> classType) {
		List reverseList = this.findAll(classType);
		Collections.reverse(reverseList);
		return reverseList;
	}

	@Override
	public <T> T merge(T entity) throws DataAccessException {
		return this.entityManager.merge(entity);
	}

	@Override
	public List queryFind(String queryString) {
		return queryFind(queryString, (Object[]) null);
	}

	@Override
	public List queryFind(String queryString, Object ... values) {
		Query query = this.entityManager.createQuery(queryString);

		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		return query.getResultList();
	}
}
