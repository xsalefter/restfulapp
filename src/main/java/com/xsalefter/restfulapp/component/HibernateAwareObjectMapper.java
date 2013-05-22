package com.xsalefter.restfulapp.component;

import java.io.Serializable;

import javax.inject.Named;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Named
public class HibernateAwareObjectMapper extends ObjectMapper 
implements Serializable {

	private static final long serialVersionUID = -5047384593751065581L;

	public HibernateAwareObjectMapper() {
		Hibernate4Module hibernate4Module = new Hibernate4Module();
		super.registerModule(hibernate4Module);
		super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		super.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	}

	public void setPrettyPrint(boolean prettyPrint) {
		configure(SerializationFeature.INDENT_OUTPUT, prettyPrint);
	}
}
