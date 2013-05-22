package com.xsalefter.restfulapp.component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;

@Named
@Singleton
public final class ResponseBuilder {

	private HibernateAwareObjectMapper objectMapper;

	@Inject
	public ResponseBuilder(HibernateAwareObjectMapper hibernateAwareObjectMapper) {
		this.objectMapper = hibernateAwareObjectMapper;
	}

	private final String buildResponse(Response response) {
		try {
			return this.objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public final String ok() {
		Response response = new Response();
		response.setHttpCode(200);
		response.setMessage("OK");
		return buildResponse(response);
	}

	public final String ok(final String message) {
		Response response = new Response();
		response.setHttpCode(200);
		response.setMessage(message);
		return buildResponse(response);
	}

	public final String ok(final Object data) {
		Response response = new Response();
		response.setHttpCode(200);
		response.setMessage("OK");
		response.setData(data);
		return buildResponse(response);
	}

	public final String ok(final String message, final int applicationCode) {
		Response response = new Response();
		response.setHttpCode(200);
		response.setMessage(message);
		response.setApplicationCode(applicationCode);
		return buildResponse(response);
	}

	public final String ok(final String message, final Object data) {
		Response response = new Response();
		response.setHttpCode(200);
		response.setMessage(message);
		response.setData(data);
		return buildResponse(response);
	}

	public final String created() {
		Response response = new Response();
		response.setHttpCode(201);
		response.setMessage("Created");
		return buildResponse(response);
	}

	public final String created(final String message) {
		Response response = new Response();
		response.setHttpCode(201);
		response.setMessage(message);
		return buildResponse(response);
	}

	public final String created(Object data) {
		Response response = new Response();
		response.setHttpCode(201);
		response.setMessage("Created");
		response.setData(data);
		return buildResponse(response);
	}

	public final String badRequest(final Object data) {
		Object result = null;
		if (data instanceof Collection) {
			Map<String, Object> local = new HashMap<String, Object>();
			local.put("error", data);
			result = local;
		} else {
			result = data.toString();
		}

		Response response = new Response();
		response.setHttpCode(400);
		response.setMessage("Bad Request.");
		response.setData(result);
		return buildResponse(response);
	}
}
