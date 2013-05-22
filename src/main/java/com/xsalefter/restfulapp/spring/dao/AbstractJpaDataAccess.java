package com.xsalefter.restfulapp.spring.dao;

import java.beans.Introspector;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import com.xsalefter.restfulapp.dao.DataAccess;
import com.xsalefter.restfulapp.entity.AbstractEntity;

public abstract class AbstractJpaDataAccess<E extends AbstractEntity> 
implements DataAccess<E> {
	private Class<E> entityClass;

	public AbstractJpaDataAccess(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	@Override
	@Transactional
	public List<E> findAll() {
		final String entityName = this.entityClass.getSimpleName();
		final String entityAlias = Introspector.decapitalize(entityName);
		final String query = "select " + entityAlias + " from " + entityName + " " + entityAlias;
		return this.getEntityManager().createQuery(query, this.entityClass).getResultList();
	}

	@Override
	@Transactional
	public E getById(String id) {
		return this.getEntityManager().find(this.entityClass, id);
	}

	@Override
	@Transactional
	public void save(E entity) {
		this.getEntityManager().persist(entity);
	}

	@Override
	@Transactional
	public void update(E entity) {
		if (!this.getEntityManager().contains(entity))
			entity = this.getEntityManager().merge(entity);

		this.getEntityManager().persist(entity);
	}

	@Override
	@Transactional
	public void delete(E entity) {
		if (!this.getEntityManager().contains(entity)) 
			entity = this.getEntityManager().merge(entity);
		this.getEntityManager().remove(entity);
	}

	@Override
	@Transactional
	public void delete(String id) throws EntityNotFoundException {
		try {
			E entity = getEntityManager().getReference(this.entityClass, id);
			getEntityManager().remove(entity);
		} catch (EntityNotFoundException e) {
			throw e;
		}
	}
}
