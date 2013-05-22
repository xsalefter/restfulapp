package com.xsalefter.restfulapp.spring.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xsalefter.restfulapp.dao.SignInFailedException;
import com.xsalefter.restfulapp.dao.UserDAO;
import com.xsalefter.restfulapp.entity.User;

@Repository
public class UserDAOImpl extends AbstractJpaDataAccess<User> implements UserDAO {

	private EntityManager entityManager;

	public UserDAOImpl() {
		super(User.class);
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Transactional
	@Override
	public User setDefaultPassword(String userId, String defaultPassword) {
		User user = this.getEntityManager().find(User.class, userId);
		user.setPassword(defaultPassword);
		getEntityManager().persist(user);
		return user;
	}

	@Transactional
	@Override
	public User signIn(String email, String password) 
	throws SignInFailedException {
		try {
			return this.entityManager.
					createQuery("from User u where u.email=:email and u.password=:pass", User.class).
					setParameter("email", email).
					setParameter("pass", password).
					getSingleResult();
		} catch (NoResultException e) {
			throw new SignInFailedException(e);
		}
	}
}
