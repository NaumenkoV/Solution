package com.softserveinc.ita.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:web-ui-spring-config.xml"})
public class OtherPagesControllerTest {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetToTheQuestionsReturnsExpectedView() throws Exception {
        mockMvc.perform(get("/ui/questions/"))
                .andExpect(status().isOk()).andExpect(view().name("questions"));
    }
    @Test
    public void testGetToTheGroupsReturnsExpectedView() throws Exception {
        mockMvc.perform(get("/ui/groups/"))
                .andExpect(status().isOk()).andExpect(view().name("groups"));
    }
    @Test
    public void testGetToTheInterviewReturnsExpectedView() throws Exception {
        mockMvc.perform(get("/ui/interview/"))
                .andExpect(status().isOk()).andExpect(view().name("interview/pages/app"));
    }
    @Test
    public void testGetToTheUsersReturnsExpectedView() throws Exception {
        mockMvc.perform(get("/ui/users/"))
                .andExpect(status().isOk()).andExpect(view().name("users"));
    }
}
