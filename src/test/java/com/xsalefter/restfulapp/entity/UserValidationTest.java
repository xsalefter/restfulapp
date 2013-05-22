package com.xsalefter.restfulapp.entity;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xsalefter.restfulapp.TestUtility;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath*:/META-INF/spring-core-config-test.xml")
public class UserValidationTest {

	@Inject
	private Validator validator;

	@Test
	public void set2CharactersToFirstNameShouldProduceValidUser() {
		User user = new User("as", "another", "user@object.com");
		user.setPassword("plainpassword");
		Set<ConstraintViolation<User>> errors = this.validator.validate(user);
		TestUtility.printViolations(errors);

		assertThat(errors, is(notNullValue()));
		assertThat(errors.isEmpty(), is(true));
	}

	@Test
	public void set1CharacterToLastNameShouldProduceInvalidUserObject() {
		User user = new User("my", "I", "some@emailaddress.com");
		user.setPassword("plaintext");
		Set<ConstraintViolation<User>> errors = this.validator.validate(user);

		assertThat(errors, is(notNullValue()));
		assertThat(errors.size(), is(1));
		assertTrue(TestUtility.violations(errors).containMessage("size must be between 2 and 30"));
	}
}
