package com.example.demo;

import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class CartControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void addToCart() throws Exception {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("Round Widget");
        cartRequest.setItemId(1);
        cartRequest.setQuantity(1);

        String inputJson = super.mapToJson(cartRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/cart/addToCart").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void addToCartInvalid() throws Exception {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("No name");
        cartRequest.setItemId(1);
        cartRequest.setQuantity(1);

        String inputJson = super.mapToJson(cartRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/cart/addToCart").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void removeFromCart() throws Exception {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("Round Widget");
        cartRequest.setItemId(1);
        cartRequest.setQuantity(1);

        String inputJson = super.mapToJson(cartRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/cart/removeFromCart").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void removeFromCartInvalid() throws Exception {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("No name");
        cartRequest.setItemId(1);
        cartRequest.setQuantity(1);

        String inputJson = super.mapToJson(cartRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/cart/removeFromCart").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }
}
