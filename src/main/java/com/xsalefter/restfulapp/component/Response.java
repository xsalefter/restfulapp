package com.xsalefter.restfulapp.component;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 4613353218531900105L;

	private int httpCode;
	private int applicationCode;
	private String message;
	private Object data;

	public Response() {}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public int getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(int applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
