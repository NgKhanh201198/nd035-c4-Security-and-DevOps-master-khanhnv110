package com.example.demo;

import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserControllerTest extends AbstractTest {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void findById() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/id/1").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void findByIdInvalid() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/id/11").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void findByUserName() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/Round Widget").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void findByUserNameInvalid() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/No name").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("KhanhNV110_t1");
        createUserRequest.setPassword("t123456");
        createUserRequest.setConfirmPassword("t123456");

        String inputJson = super.mapToJson(createUserRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUserWithPasswordInvalidLength() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("KhanhNV110_t2");
        createUserRequest.setPassword("t12345");
        createUserRequest.setConfirmPassword("t12345");

        String inputJson = super.mapToJson(createUserRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUserWithPasswordNotMatch() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("KhanhNV110_t3");
        createUserRequest.setPassword("t123456");
        createUserRequest.setConfirmPassword("t654321");

        String inputJson = super.mapToJson(createUserRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUserWithUsernameAlreadyExists() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("KhanhNV110_t456");
        createUserRequest.setPassword("t123456");
        createUserRequest.setConfirmPassword("t123456");

        String inputJson = super.mapToJson(createUserRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        CreateUserRequest createUserRequest1 = new CreateUserRequest();
        createUserRequest1.setUsername("KhanhNV110_t456");
        createUserRequest1.setPassword("t123456");
        createUserRequest1.setConfirmPassword("t123456");

        String inputJson1 = super.mapToJson(createUserRequest1);
        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.post("/api/user/create").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson1)).andReturn();
        assertEquals(400, mvcResult1.getResponse().getStatus());
    }
}
