package com.softserveinc.ita.mvc.impl;

import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.mvc.MvcGroupBaseTest;
import com.softserveinc.ita.utils.JsonUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@FixMethodOrder(MethodSorters.DEFAULT)
public class MvcGroupTests extends MvcGroupBaseTest {

    private MockMvc mockMvc;

    @Autowired
    private JsonUtil jsonUtil;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetGroupsByStatusAndExpectIsOk() throws Exception {
        mockMvc.perform(
                get("/status/BOARDING")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGroupsAndExpectJsonType() throws Exception {
        mockMvc.perform(
                get("/status/BOARDING")
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetCoursesAndExpectIsOk() throws Exception {
        mockMvc.perform(
                get("/courses")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCoursesAndExpectJsonType() throws Exception {
        mockMvc.perform(
                get("/courses")
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPostGroupAndExpectJsonType() throws Exception {
        Group group = new Group("3");
        group.setCourse(new Course("Java"));
        String jsonGroup = jsonUtil.toJson(group);
        mockMvc.perform(post("/addGroup")
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllGroupsAndExpectIsOk() throws Exception {
        mockMvc.perform(
                get("/allGroups")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllGroupsAndExpectJsonType() throws Exception {
        mockMvc.perform(
                get("/allGroups")
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetGroupByExistingIdAndExpectIsOk() throws Exception {
        mockMvc.perform(
                get("/id1")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGroupByExistingIdAndExpectJsonType() throws Exception {
        mockMvc.perform(
                get("/id1")
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetGroupByNotExistingIdAndExpectInternalServerError() throws Exception {
        mockMvc.perform(
                get("/notExistingId")
        ).andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteGroupByIdAndExpectIsOk() throws Exception {
        mockMvc.perform(
                delete("/id4")
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteGroupByIdAndExpectJsonType() throws Exception {
        mockMvc.perform(
                delete("/id3")
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testPutGroupAndExpectIsOkAndJSONType() throws Exception {
        Group group = new Group("id1");
        group.setCourse(new Course("Java"));
        String jsonGroup = jsonUtil.toJson(group);
        mockMvc.perform(
                put("/editGroup")
                        .content(jsonGroup)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

