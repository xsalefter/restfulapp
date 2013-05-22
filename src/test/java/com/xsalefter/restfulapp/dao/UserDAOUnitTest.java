package com.xsalefter.restfulapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.xsalefter.restfulapp.entity.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

/**
 * Only for example. Unit testing in interface some brings a doubt among developers.
 */
public class UserDAOUnitTest {

	private UserDAO userDAO;

	@Before
	public void init() {
		this.userDAO = Mockito.mock(UserDAO.class);
	}

	@Test
	public void findAllTest() {
		List<User> users = new ArrayList<>();
		users.add(new User("1", "my", "name", "is", "user1"));
		users.add(new User("2", "my", "name", "is", "user2"));
		when(this.userDAO.findAll()).thenReturn(users);

		List<User> toTest = this.userDAO.findAll();
		assertThat(toTest, is(notNullValue()));
		assertThat(toTest.size(), is(2));
	}
}
