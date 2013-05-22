package com.xsalefter.restfulapp.entity;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/** Not so useful in real world scenario. */
public class UserUnitTest {

	@Test
	public void example() {
		User user = new User("some", "name", "some@name.com");
		assertThat(user.getFirstName(), is("some"));
		assertThat(user.getLastName(), is("name"));
		assertThat(user.getEmail(), is("some@name.com"));
	}
}
