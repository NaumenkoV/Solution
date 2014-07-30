package com.softserveinc.ita.mvc;

import com.softserveinc.ita.entity.NotificationJSONInfo;
import com.softserveinc.ita.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class ApplicantsNotificationTests extends BaseMVCTest {

    private MockMvc mockMvc;
    private List<NotificationJSONInfo> testInfoList;
    private String jsonInfo;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private JsonUtil jsonUtil;

    @Before
    public void setup() {
        NotificationJSONInfo info1 = new NotificationJSONInfo("applicantID_1", "groupID_1", "hrID_1");
        NotificationJSONInfo info2 = new NotificationJSONInfo("applicantID_2", "groupID_2", "hrID_2");
        NotificationJSONInfo info3 = new NotificationJSONInfo("applicantID_3", "groupID_3", "hrID_3");

        testInfoList = new ArrayList();
        testInfoList.add(info1);
        testInfoList.add(info2);
        testInfoList.add(info3);

        jsonInfo = jsonUtil.toJson(testInfoList);
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void testPostToApplicantsNotificationReturnsOK() throws Exception {
        System.out.println(jsonInfo);
        mockMvc.perform(post("/").content(jsonInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostToApplicantsNotificationReturnsPostedIDList() throws Exception {
        MvcResult result = mockMvc.perform(post("/").content(jsonInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(jsonInfo, result.getResponse().getContentAsString());
    }
   }