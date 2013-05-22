package com.xsalefter.restfulapp.dao;

import com.xsalefter.restfulapp.entity.User;

/**
 * Data access for {@link User}.
 * @author xsalefter
 */
public interface UserDAO extends DataAccess<User> {

	/**
	 * Set default password for {@link User}.
	 * @param userId userId to change password.
	 * @param defaultPassword password value.
	 * @return {@link User} object with updated password value.
	 */
	User setDefaultPassword(String userId, String defaultPassword);

	/** TODO: This would be handle by spring security.
	 * @param email email address.
	 * @param password plain password (change to bcrypt later)
	 * @return {@link User} object. 
	 * @throws SignInFailedException if email and or password doesn't match.
	 */
	User signIn(String email, String password) throws SignInFailedException;
}
