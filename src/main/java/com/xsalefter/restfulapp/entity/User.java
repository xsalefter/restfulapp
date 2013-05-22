package com.xsalefter.restfulapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.annotations.VisibleForTesting;

@Entity
@Table(name="acl_user")
public class User extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -2793971017682471770L;

	@Column(name="first_name", length=30, nullable=false)
	@NotNull @NotEmpty @Size(min=2, max=30)
	private String firstName;

	@Column(name="last_name", length=30, nullable=false)
	@NotNull @NotEmpty @Size(min=2, max=30)
	private String lastName;

	@Column(name="email_address", length=30, nullable=false)
	@NotNull @NotEmpty @Size(min=2, max=30)
	private String email;

	@Column(name="password", nullable=false)
	@NotNull @NotEmpty
	private String password;

	public User() {}

	public User(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	/**
	 * Do not use outside of test environment.
	 */
	@VisibleForTesting
	public User(String id, String firstName, String lastName, String email, String password) {
		super();
		super.setId(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
