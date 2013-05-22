package com.xsalefter.restfulapp.web;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xsalefter.restfulapp.component.HibernateAwareObjectMapper;
import com.xsalefter.restfulapp.component.ResponseBuilder;
import com.xsalefter.restfulapp.dao.SignInFailedException;
import com.xsalefter.restfulapp.dao.UserDAO;
import com.xsalefter.restfulapp.entity.User;

@Controller
@RequestMapping("/api")
public class UserREST {

	private UserDAO userDAO;
	private ResponseBuilder responseBuilder;
	private HibernateAwareObjectMapper objectMapper;

	@Inject
	public UserREST(
			UserDAO userDAO, 
			ResponseBuilder responseBuilder,
			HibernateAwareObjectMapper objectMapper) {
		this.userDAO = userDAO;
		this.responseBuilder = responseBuilder;
		this.objectMapper = objectMapper;
	}

	@RequestMapping(value="/user/list.json", method=RequestMethod.GET,  
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	@ResponseBody
	public String list() {
		return this.responseBuilder.ok(this.userDAO.findAll());
	}

	@RequestMapping(value="/user/{userId}", method=RequestMethod.GET,
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	@ResponseBody
	public String getById(@PathVariable String userId) {
		return this.responseBuilder.ok(this.userDAO.getById(userId));
	}

	@RequestMapping(value="/user", method=RequestMethod.POST,
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	@ResponseBody
	public String create(@RequestBody Map<String, String> requestParam) {
		final String requestBody = requestParam.get("user");
		try {
			User user = this.objectMapper.readValue(requestBody, User.class);
			this.userDAO.save(user);
			return this.responseBuilder.ok(user);
		} catch (IOException e) {
			return this.responseBuilder.badRequest(e.getMessage());
		}
	}

	@RequestMapping(value="/user", method=RequestMethod.PUT,
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	public String update(@RequestBody Map<String, String> requestParam) {
		final String requestBody = requestParam.get("user");
		try {
			User user = this.objectMapper.readValue(requestBody, User.class);
			if (user.getId() == null)
				return this.responseBuilder.badRequest("Request body should have user identifier.");

			User toUpdate = this.userDAO.getById(user.getId());
			toUpdate.setFirstName(user.getFirstName());
			toUpdate.setLastName(user.getLastName());
			toUpdate.setEmail(user.getEmail());

			this.userDAO.update(toUpdate);

			return this.responseBuilder.ok(toUpdate);
		} catch (IOException e) {
			return this.responseBuilder.badRequest(e.getMessage());
		}
	}

	@RequestMapping(value="/user/{userId}.json", method=RequestMethod.DELETE,
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	public String delete(@PathVariable String userId) {
		this.userDAO.delete(userId);
		final String msg = "User with id " + userId + " was deleted.";
		return this.responseBuilder.ok(msg);
	}

	@RequestMapping(value="/user/reset-password/{userId}.json", method=RequestMethod.PUT,
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	public String resetPassword(@PathVariable String userId) {
		User user = this.userDAO.setDefaultPassword(userId, "default");
		return this.responseBuilder.ok(user);
	}

	@RequestMapping(value="/user/signin.json", method=RequestMethod.POST,
			produces={"application/json; charset=UTF-8"}, consumes={"application/json; charset=UTF-8"})
	public String signIn(Map<String, String> requestParam) {
		final String requestBody = requestParam.get("user");

		try {
			User user = this.objectMapper.readValue(requestBody, User.class);
			this.userDAO.signIn(user.getEmail(), user.getPassword());

			return this.responseBuilder.ok(user);
		} catch (IOException | SignInFailedException e) {
			return this.responseBuilder.badRequest(e.getMessage());
		}
	}
}
