package com.xsalefter.restfulapp.web;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:/META-INF/spring-core-config-test.xml"})
public class UserRESTIntegrationTest {

	@Inject private UserREST userREST;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userREST).build();
    }

    @Test @org.junit.Ignore
    public void getAllUsers() throws Exception {
        this.mockMvc.perform(get("/api/user/list.json").header("Content-Type", "application/json;charset=utf-8")).
        	andDo(MockMvcResultHandlers.print()).
        	andExpect(status().isOk()).
        	andExpect(content().contentType("application/json"));
    }
}
