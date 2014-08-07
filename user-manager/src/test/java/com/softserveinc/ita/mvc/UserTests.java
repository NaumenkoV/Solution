package com.softserveinc.ita.mvc;

import com.softserveinc.ita.entity.User;

import com.softserveinc.ita.entity.exceptions.ExceptionJSONInfo;
import com.softserveinc.ita.utils.JsonUtil;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserTests extends BaseMVCTest {
    private MockMvc mockMvc;

    @Autowired
    private JsonUtil jsonUtil;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;
    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void testDeleteUserByIDAndExpectedIsOK() throws Exception {
        String userID = "121";
        mockMvc.perform(delete(  "/" + userID))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserByIDAndExpectedDeletedSuccessfully() throws Exception {
        String userID = "122";

        mockMvc.perform(delete(  "/" + userID))
               .andExpect(content().string(userID));
    }

    @Test
    public void testGetValidUserByIDAndExpectIsOK() throws Exception {
        String userID = "1";
        User testUser = new User(userID);
        String expectedJsonUser = jsonUtil.toJson(testUser);
        MvcResult result = mockMvc.perform(
                get("/{userID}", userID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(expectedJsonUser, result.getResponse().getContentAsString());         //compare expected test User and actual.
    }

    @Test
    public void testGetNonExistentUserAndGetInternalServerError() throws Exception {
        String userID = "-1";
        mockMvc.perform(
                get("/{userID}", userID))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetValidUserByEmailAndExpectIsOK() throws Exception {
        String testEmail = "test@test.com";
        User testUser = new User("1");
        testUser.setEmail(testEmail);
        String expectedJsonUser = jsonUtil.toJson(testUser);
        MvcResult result = mockMvc.perform(
                get("/param").param("email",testEmail).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(expectedJsonUser, result.getResponse().getContentAsString());         //compare expected test User and actual.
    }

    @Test
    public void testGetNonExistentUserByEmailAndGetInternalServerError() throws Exception {
        String testEmail = "no@such.com";
        mockMvc.perform(
                get("/param").param("email",testEmail))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetAllUsersIDAndExpectIsOK() throws Exception {
        mockMvc.perform(get("/userId")).
                andExpect(status().isOk());
    }

    @Test
    public void testGetAllRolesIDAndExpectIsOK() throws Exception {
        mockMvc.perform(get("/roles")).
                andExpect(status().isOk());
    }

    @Test
    public void testEditUserWithEmptyFieldAndExpectBadRequest() throws Exception {
        User user = new User("id1");
        String jsonUser = jsonUtil.toJson(user);
        mockMvc.perform(
                put("/").
                        content(jsonUser).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testEditUserWithNotExistingIdAndExpectNotFound() throws Exception {
        User user = new User("id4");
        user.setName("Andrey");
        String jsonUser = jsonUtil.toJson(user);
        mockMvc.perform(
                put("/").
                        content(jsonUser).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testEditUserWithExistingIdAndExpectIsoK() throws Exception {
        User user = new User("id3");
        user.setName("Andrey");
        String jsonUser = jsonUtil.toJson(user);
        mockMvc.perform(
                put("/").
                        content(jsonUser).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testEditUserWithExistingIdAndExpectEqualsWithResult() throws Exception{
        User user = new User("id3");
        user.setName("Andrey");
        String jsonUser = jsonUtil.toJson(user);
        mockMvc.perform(
                put("/").
                        content(jsonUser).
                        contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().string(jsonUser));
    }
    @Test
    public void testPostNewUserAndExpectIsCreated() throws Exception {
        User testUser = new User("3");
        String jsonUser = jsonUtil.toJson(testUser);
        mockMvc.perform(post("/")
                .content(jsonUser)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetUserListAndExpectIsOk() throws Exception {
        mockMvc.perform(
                get("/")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserListAndExpectJson() throws Exception {
        mockMvc.perform(
                get("/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetUserListAndExpectPreDefinedList() throws Exception {
        List<User> sampleUserList = new ArrayList<>();
        Collections.addAll(sampleUserList, new User("id3"), new User("idY"), new User("id09z"));
        String expectedResult = jsonUtil.toJson(sampleUserList);
        mockMvc.perform(
                get("/"))
                .andExpect(content().string(expectedResult));
    }
    @Test
    public void testGetUserByIDAndExpectReasonWasConvertedToJson() throws Exception{
        mockMvc.perform(
                get("/-1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonUtil.toJson(new ExceptionJSONInfo("Invalid user ID"))));
    }
}