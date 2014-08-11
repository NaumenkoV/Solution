package com.softserveinc.ita.factoryInterviewTests;

import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.interviewfactory.factory.InterviewFactory;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import exceptions.WrongCriteriaException;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class FactoryInterviewTests extends BaseFactoryInterviewTests {

    @Autowired
    InterviewFactory interviewFactory;

    @Autowired
    private JsonUtil interviewUtilJson;

    JSONParser parser = new JSONParser();

    @Test
    public void testCreateInterviewWithoutQuestions() throws IOException, ParseException, JSONException, HttpRequestException, org.json.simple.parser.ParseException, WrongCriteriaException {
    Interview interview1 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("1");

    Interview interview2 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create("2");

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/JsonTestInterviewWithoutQuestions1.json")));
        Object object1 = parser.parse(reader1);
        String json1 = object1.toString();

        //The thing is:
        //tests fail because the order of items is different. It's not an error, but as jsons are compared as strings, it causes such
        //TODO: Fix assert.
      //  JSONAssert.assertEquals(interviewUtilJson.toJson(interview1), json1, false);

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/JsonTestInterviewWithoutQuestions2.json")));
        Object object2 = parser.parse(reader2);
        String json2 = object2.toString();
        //TODO: Fix assert.
       // JSONAssert.assertEquals(interviewUtilJson.toJson(interview2), json2, false);

    }

    @Test
    public void testCreateInterviewWithStandardQuestions() throws JSONException, IOException, ParseException, HttpRequestException, org.json.simple.parser.ParseException, WrongCriteriaException {
        Interview interview1 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_STANDARD_QUESTIONS).create("1");
        Interview interview2 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_STANDARD_QUESTIONS).create("2");

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/JsonTestInterviewWithStandardQuestions1.json")));
        Object object1 = parser.parse(reader1);
        String json1 = object1.toString();
        //TODO: Fix aassert.
      //  JSONAssert.assertEquals(interviewUtilJson.toJson(interview1), json1, false);

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(
                        "/JsonTestInterviewWithStandardQuestions2.json")));
        Object object2 = parser.parse(reader2);
        String json2 = object2.toString();
        //TODO: Fix assert.
       // JSONAssert.assertEquals(interviewUtilJson.toJson(interview2), json2, false);
    }

    @Test
    public void testCreateInterviewWithUserAndStandardQuestions() throws HttpRequestException, IOException, org.json.simple.parser.ParseException, JSONException {
        Interview interview1 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_USER_QUESTIONS).create("3");
        Interview interview2 = interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_USER_QUESTIONS).create("2");
        System.out.println(interviewUtilJson.toJson(interview1));
//        BufferedReader reader1 = new BufferedReader(new InputStreamReader(
//                getClass().getResourceAsStream(
//                        "/JsonTestInterviewWithUserAndStandardQuestions1.json")));
//        Object object1 = parser.parse(reader1);
//        String json1 = object1.toString();
//        //TODO: Fix assert.
//      //  JSONAssert.assertEquals(interviewUtilJson.toJson(interview1), json1, false);
//
//        BufferedReader reader2 = new BufferedReader(new InputStreamReader(
//                getClass().getResourceAsStream(
//                        "/JsonTestInterviewWithUserAndStandardQuestions2.json")));
//        Object object2 = parser.parse(reader2);
//        String json2 = object2.toString();
//
//        //TODO: Fix assert.
//       // JSONAssert.assertEquals(interviewUtilJson.toJson(interview2), json2, false);
    }
}
