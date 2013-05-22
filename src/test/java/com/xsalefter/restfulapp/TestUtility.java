package com.xsalefter.restfulapp;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestUtility {

	private static final Logger logger = LoggerFactory.getLogger(TestUtility.class);

	public static class Violation<T> {
		private Set<ConstraintViolation<T>> constraintViolations;
		private String field;

		public Violation(Set<ConstraintViolation<T>> violations) {
			this.constraintViolations = violations;
		}

		public Violation<T> in(String field) {
			this.field = field;
			return this;
		}

		public boolean containMessage(final String message) {
			if (field == null) {
				for (ConstraintViolation<T> v : this.constraintViolations) {
					if (v.getMessage().equals(message)) {
						return true;
					}
				}
			} else {
				for (ConstraintViolation<T> v : this.constraintViolations) {
					for (Node node : v.getPropertyPath()) {
						if (node.getName().equals(this.field)) {
							if (v.getMessage().equals(message)) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	}

	/**
	 * Useful to print {@link Set} of {@link ConstraintViolation} error to 
	 * detailed message in Slf4j {@link Logger} with detailed {@link Node} and 
	 * {@link ConstraintViolation#getMessage()}.
	 * 
	 * @param errors {@link Set} of Bean Validation's {@link ConstraintViolation}, 
	 * which is usually get from {@link Validator#validate(Object, Class...)}.
	 */
	public static <T> void printViolations(Set<ConstraintViolation<T>> errors) {
		for (ConstraintViolation<T> error : errors) {
			for (Node node : error.getPropertyPath()) {
				logger.debug("Node: {} - Message: {}", node.getName(), error.getMessage());
			}
		}
	}

	public static void printWildcardedViolations(Set<ConstraintViolation<?>> errors) {
		for (ConstraintViolation<?> error : errors) {
			for (Node node : error.getPropertyPath()) {
				logger.debug("Node: {} - Message: {}", node.getName(), error.getMessage());
			}
		}
	}

	/**
	 * Used to check whether one of {@link ConstraintViolation#getMessage()} is 
	 * contained in {@link Set} of {@link ConstraintViolation} method parameter. 
	 * Usage:<br/>
	 * <pre>
	 * org.junit.Assert.assertTrue( TestUtility.violations(errors).containMessage("some message") );
	 * </pre>
	 * 
	 * @param errors {@link Set} of Bean Validation's {@link ConstraintViolation}, 
	 * which is usually get from {@link Validator#validate(Object, Class...)}.
	 * @return {@link Violation} object, so we could invoke 
	 * {@link Violation#containMessage(String)} method.
	 */
	public static <T> Violation<T> violations(Set<ConstraintViolation<T>> errors) {
		return new Violation<T>(errors);
	}
}
