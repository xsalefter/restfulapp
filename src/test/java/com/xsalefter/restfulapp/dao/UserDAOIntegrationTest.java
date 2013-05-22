package com.xsalefter.restfulapp.dao;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import com.xsalefter.restfulapp.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath*:/META-INF/spring-core-config-test.xml")
public class UserDAOIntegrationTest {

	@Inject
	private UserDAO userDAO;

	@Test
	public void findAllTest() {
		List<User> users = this.userDAO.findAll();
		assertThat(users, is(notNullValue()));
		assertThat(users.size(), is(3));
	}

	@Test
	public void getByIdTest() {
		User user = this.userDAO.getById("2");
		assertThat(user, is(notNullValue()));
		assertThat(user.getFirstName(), is("ade"));
		assertThat(user.getLastName(), is("giok"));
	}

	@Test
	public void saveTest() {
		User user = new User("some", "one", "someone@email.com");
		user.setPassword("some password");
		this.userDAO.save(user);
		assertThat(user.getId(), is(notNullValue())); // Generated as GUID/UUID.
	}

	@Test
	public void updateTest() {
		User user = this.userDAO.getById("1");
		assertThat(user.getEmail(), is("xsalefter@yahoo.com"));

		user.setEmail("xsalefter@gmail.com");
		this.userDAO.update(user);

		user = this.userDAO.getById("1");
		assertThat(user.getEmail(), is("xsalefter@gmail.com"));
	}

	@Test
	public void deleteTest() {
		this.userDAO.delete("2");
		User user = this.userDAO.getById("2");

		assertThat(user, is(nullValue()));
	}

	@Test
	public void resetPasswordTest() {
		this.userDAO.setDefaultPassword("1", "default");
		User user = this.userDAO.getById("1");
		assertThat(user.getPassword(), is("default"));
	}

	@Test
	public void signInTest() throws SignInFailedException {
		User user = this.userDAO.signIn("foo@bar.com", "foobar");
		assertThat(user, is(notNullValue()));
		assertThat(user.getEmail(), is("foo@bar.com"));
	}
}
