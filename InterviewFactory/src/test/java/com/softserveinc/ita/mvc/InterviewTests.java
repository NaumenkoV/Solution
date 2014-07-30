package com.softserveinc.ita.mvc;

import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.interviewfactory.dao.mock.InterviewDAOMock;
import com.softserveinc.ita.interviewfactory.factory.InterviewFactory;
import com.softserveinc.ita.interviewfactory.service.interviewServices.InterviewService;
import com.softserveinc.ita.interviewfactory.service.questionInformationServices.QuestionsInformationServices;
import com.softserveinc.ita.interviewfactory.service.questionsBlockServices.QuestionsBlockServices;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import exceptions.WrongCriteriaException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class InterviewTests extends BaseMVCTest {

    private MockMvc mockMvc;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private QuestionsBlockServices questionsBlockServices;

    @Autowired
    private QuestionsInformationServices questionsInformationServices;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private InterviewFactory interviewFactory;

    @Autowired
    private JsonUtil jsonUtil;

    @Autowired
    private JsonUtil interviewUtilJson;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetInterviewByAppointmentIDAndExpectIsAccepted() throws Exception {

        mockMvc.perform(
                get("/" + "1")
        )
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetInterviewByExistingAppointmentIdAndExpectOk() throws Exception {
        Interview interview = interviewService.getInterviewByAppointmentID("1");
        MvcResult ExpectingObject = mockMvc.perform(
                get("/1")
        )
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(jsonUtil.toJson(interview), ExpectingObject.getResponse().getContentAsString());
    }

    @Test
    public void testGetInterviewByNotExistingAppointmentIdAndExpectOk() throws Exception {
        Interview interview = interviewService.getInterviewByAppointmentID("3");
        MvcResult ExpectingObject = mockMvc.perform(
                get("/3")
        )
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(jsonUtil.toJson(interview), ExpectingObject.getResponse().getContentAsString());
    }

    @Test
    public void testGetInterviewByApplicantIdAndExpectOk() throws Exception {
        List<Interview> interviewList = interviewService.getInterviewByApplicantID("1");
        MvcResult ExpectingObject = mockMvc.perform(
                get("/applicants/1")
        )
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(jsonUtil.toJson(interviewList), ExpectingObject.getResponse().getContentAsString());

    }

    @Test
    public void testRemoveInterviewByAppointmentIdAndExpectOk() throws Exception {

        mockMvc.perform(
                delete("/1")
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateInterviewByAppointmentIdAndExpectOk() throws Exception {

        Interview interview1 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("1");
        String interviewJson = jsonUtil.toJson(interview1);

        mockMvc.perform(
                put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(interviewJson)
        )
                .andExpect(status().is(204));

    }

    @Test
    public void testGetInterviewByAppointmentIdAndExpectInterviewEqualExpectedOne() throws HttpRequestException, WrongCriteriaException{
        Interview actual = interviewService.getInterviewByAppointmentID("1");
        Interview expected = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("1");
        expected.setInterviewId("1");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetInterviewByApplicantIdAndExpectInterviewEqualExpectedOne() throws HttpRequestException, WrongCriteriaException {
        List<Interview> actual = interviewService.getInterviewByApplicantID("1");
        Interview expected = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("5");
        expected.setInterviewId("5");
        assertEquals(expected, actual.get(0));
    }

    Cookie[] cookies = new Cookie[] { new Cookie("userId", "1"), new Cookie("appointmentId", "1") };

    @Test
    public void testAddQuestionInformationAndExpectOk() throws Exception {

        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");

        String questionInformationJson = jsonUtil.toJson(questionInformation);

        mockMvc.perform(
                post("/interviewing/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionInformationJson)
                .cookie(cookies)
        )
                .andExpect(status().isAccepted());

        Interview interview = InterviewDAOMock.interviewsList.get(0);
        Set<QuestionsBlock> allQuestionsBlocksActual = interview.getQuestionsBlocks();
        Iterator<QuestionsBlock> it = allQuestionsBlocksActual.iterator();
        QuestionsBlock questionsBlockActual = it.next();

        Set<QuestionInformation> questionInformationSet = questionsBlockActual.getQuestions();
        Iterator<QuestionInformation> it2 = questionInformationSet.iterator();
        QuestionInformation questionInformationActual = it2.next();

        Assert.assertEquals(questionInformationActual, questionInformation);

    }

    @Test
    public void testUpdateFinalCommentInQuestionsBlockAndExpectOk() throws Exception {

        FinalComment finalComment = new FinalComment();
        finalComment.setBonusPoints(3);
        finalComment.setFinalComment("final comment");
        finalComment.setInterviewId("1");

        String finalCommentJson = jsonUtil.toJson(finalComment);

        mockMvc.perform(
                put("/interviewing/final_comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(finalCommentJson)
                        .cookie(cookies)
        )
                .andExpect(status().isAccepted());

        QuestionsBlock questionsBlockActual = questionsBlockServices.getQuestionsBlockFromInterviewByUserId("1", "1");

        FinalComment finalCommentActual = new FinalComment();
        finalCommentActual.setFinalComment(questionsBlockActual.getFinalComment());
        finalCommentActual.setInterviewId(questionsBlockActual.getInterviewId());
        finalCommentActual.setBonusPoints(questionsBlockActual.getBonusPoints());

        Assert.assertEquals(finalComment, finalCommentActual);
    }

    @Test
    public void testGetInterviewResultsByInterviewIdAndExpectOk() throws Exception {

        mockMvc.perform(
                get("/1/result")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void testUpdateQuestionInformationAndExpectOk() throws Exception {

        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body corrected");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformation.setId("1");

        String questionInformationJson = jsonUtil.toJson(questionInformation);

        MvcResult ExpectingObject = mockMvc.perform(
                put("/interviewing/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(questionInformationJson)
                        .cookie(cookies)
        )
                .andExpect(status().isAccepted())
                .andReturn();

        assertEquals(questionInformation.getId(), ExpectingObject.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteQuestionInformationByIdAndExpectOk() throws Exception {

        mockMvc.perform(
                delete("/interviewing/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")
        )
                .andExpect(status().isOk());
    }


}
