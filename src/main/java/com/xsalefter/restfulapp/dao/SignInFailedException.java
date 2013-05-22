package com.xsalefter.restfulapp.dao;

public class SignInFailedException extends Exception {

	private static final long serialVersionUID = -4224289095585720185L;

	public SignInFailedException(Throwable e) {
		super(e);
	}
}
