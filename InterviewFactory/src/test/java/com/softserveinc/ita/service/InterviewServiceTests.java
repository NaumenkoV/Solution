package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.InterviewType;
import com.softserveinc.ita.interviewfactory.factory.InterviewFactory;
import com.softserveinc.ita.interviewfactory.service.interviewServices.InterviewService;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;


public class InterviewServiceTests extends BaseServiceTests {

    @Autowired
    InterviewService interviewService;

    @Autowired
    InterviewFactory interviewFactory;


    @Test
    public void testGetInterviewByIdAndExpectEqual() throws HttpRequestException, WrongCriteriaException {

        Interview interviewActual = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("5");
        Interview interviewExpected = interviewService.getInterviewByAppointmentID(interviewActual.getInterviewId());
        assertEquals(interviewActual, interviewExpected);
    }

    @Test
    public void testGetInterviewByIdAndExpectNewInterview() throws HttpRequestException, WrongCriteriaException, InterruptedException {

        Interview interviewActual = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("4");
        Interview interviewExpected = interviewService.getInterviewByAppointmentID("4");
        assertEquals(interviewActual, interviewExpected);

    }

    @Test
    public void testPutInterviewAndExpectOk() throws HttpRequestException, WrongCriteriaException {
        Interview interviewActual1 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("1");
        Interview interviewActual2 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_STANDARD_QUESTIONS).create("2");
        Interview interviewActual3 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_USER_AND_STANDARD_QUESTIONS).create("3");

        String interviewExpected1 = interviewService.putInterview(interviewActual1.getInterviewId(), InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        String interviewExpected2 = interviewService.putInterview(interviewActual2.getInterviewId(), InterviewType.INTERVIEW_WITH_STANDARD_QUESTIONS);
        String interviewExpected3 = interviewService.putInterview(interviewActual3.getInterviewId(), InterviewType.INTERVIEW_WITH_USER_AND_STANDARD_QUESTIONS);

        assertEquals(interviewActual1.getInterviewId(), interviewExpected1);
        assertEquals(interviewActual2.getInterviewId(), interviewExpected2);
        assertEquals(interviewActual3.getInterviewId(), interviewExpected3);

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPutInterviewAndExpectWrongCriteriaException() throws HttpRequestException, WrongCriteriaException {
        thrown.expect(WrongCriteriaException.class);
        thrown.expectMessage("Type is wrong");
        interviewService.putInterview("1", null);
    }

    @Test
    public void testGetInterviewByApplicantIdAndExpectOk() throws HttpRequestException, WrongCriteriaException {

        Interview interviewActual = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("5");
        List<Interview> interviewExpectedList = interviewService.getInterviewByApplicantID("5");
        assertEquals(interviewActual, interviewExpectedList.get(0));

    }

    @Test
    public void testGetAllInterviews() throws HttpRequestException {

        List<Interview> interviewsListActual = new ArrayList<>();
        Interview interview1 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("1");
        Interview interview2 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("2");
        interviewsListActual.add(interview1);
        interviewsListActual.add(interview2);

        List<Interview> interviewsListExpected= interviewService.getAllInterviews();
        assertEquals(interviewsListExpected, interviewsListActual);

    }

}
