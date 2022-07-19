package com.example.demo;

import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void submit() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/order/submit/Round Widget").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void submitWithUsernameInvalid() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/order/submit/No name").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void getOrdersForUser() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/order/history/Round Widget").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void getOrdersForUserInvalid() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/order/history/No name").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }
}
