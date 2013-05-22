package com.xsalefter.restfulapp.dao;

import java.util.List;

import com.xsalefter.restfulapp.entity.AbstractEntity;

public interface DataAccess<E extends AbstractEntity> {
	List<E> findAll();
	E getById(String id);
	void save(E entity);
	void update(E entity);
	void delete(E entity);
	void delete(String id);
}
