package com.softserveinc.ita.mvc;

import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.entity.exceptions.ExceptionJSONInfo;
import com.softserveinc.ita.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class ApplicantControllerTest extends BaseMVCTest {
    private MockMvc mockMvc;

    @Autowired
    private JsonUtil jsonUtil;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetApplicantsListAngExpectedIsOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicantsListAngExpectedValid() throws Exception {
        List<Applicant> standart = new ArrayList<>();
        standart.add(new Applicant("123"));
        standart.add(new Applicant("124"));
        standart.add(new Applicant("125"));

        String standardJson = jsonUtil.toJson(standart);

        mockMvc.perform(get("/"))
                .andExpect(content().string(standardJson));
    }

    @Test
    public void testGetApplicantAndExpectIsOk() throws Exception {
        mockMvc.perform(
                get("/id1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicantWithNotExistingIdAndExpectException() throws Exception {
        mockMvc.perform(
                get("/id4"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetApplicantAndExpectJsonType() throws Exception {
        mockMvc.perform(
                get("/id2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetApplicantWithExistingIdAndExpectCorrectApplicant() throws Exception {
        Applicant applicant = new Applicant("id2");
        String jsonApplicant = jsonUtil.toJson(applicant);
        mockMvc.perform(
                get("/id2")).andExpect(content().string(jsonApplicant));
    }

    @Test
    public void testPostNewApplicantAndExpectIsOk() throws Exception {

        Applicant applicant = new Applicant();
        String applicantJson = jsonUtil.toJson(applicant);

        mockMvc.perform(
                post("/").content(applicantJson).contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testPostNewApplicantAndGetTheSameApplicant() throws Exception {

        String applicantJson = jsonUtil.toJson(new Applicant());

        String newApplicantJson = mockMvc.perform(
                post("/")
                        .content(applicantJson).contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Applicant newApplicant = jsonUtil.fromJson(newApplicantJson, Applicant.class);

        String applicantFromServerJson = mockMvc.perform(
                get("/" + newApplicant.getId())
        )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Applicant applicantFromServer = jsonUtil.fromJson(applicantFromServerJson, Applicant.class);
        assertEquals(newApplicant, applicantFromServer);
    }

    @Test
    public void testGetApplicantIDListAndExpectIsOk() throws Exception {
        mockMvc.perform(get("/applicantID"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetApplicantIDListAndExpectJson() throws Exception {
        mockMvc.perform(get("/applicantID"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetApplicantByIDAndExpectReasonWasConvertedToJson() throws Exception{
        mockMvc.perform(
                get("/-1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonUtil.toJson(new ExceptionJSONInfo("Applicant does not exist"))));
    }
}
