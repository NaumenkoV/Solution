package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.*;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

import static junit.framework.Assert.assertEquals;

public class InterviewDAOTests extends BaseDAOTest {

    @Autowired
    private InterviewDAO interviewDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testGetInterviewById() {

        Set<QuestionsBlock> allQuestionsBlocks = new HashSet<>();
        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);

        Interview interviewExpected = new Interview();
        allQuestionsBlocks.add(questionsBlock);
        interviewExpected.setInterviewId("1");

        interviewExpected.setQuestionsBlocks(allQuestionsBlocks);
        interviewExpected.setType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        String interviewId = (String) sessionFactory.getCurrentSession().save(interviewExpected);
        Interview interviewActual = interviewDAO.getInterviewByAppointmentId(interviewId);
        assertEquals(interviewActual, interviewExpected);

    }

    @Test
    public void testPutInterview() {
        Set<QuestionsBlock> allQuestionsBlocks = new HashSet<>();
        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);

        Interview interviewExpected = new Interview();
        allQuestionsBlocks.add(questionsBlock);
        interviewExpected.setInterviewId("1");

        interviewExpected.setQuestionsBlocks(allQuestionsBlocks);
        interviewExpected.setType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        String interviewId = interviewDAO.putInterview(interviewExpected);
        Interview interviewActual = interviewDAO.getInterviewByAppointmentId(interviewId);
        assertEquals(interviewActual, interviewExpected);

    }

    @Test
    public void testGetAllInterviews() {

        Interview interview1 = new Interview();
        interview1.setInterviewId("1");
        Interview interview2 = new Interview();
        interview2.setInterviewId("2");
        Interview interview3 = new Interview();
        interview3.setInterviewId("3");
        interviewDAO.putInterview(interview1);
        interviewDAO.putInterview(interview2);
        interviewDAO.putInterview(interview3);
        List<Interview> interviewIdList = interviewDAO.getAllInterviews();
        assertEquals(3, interviewIdList.size());

    }

    @Test
    public void testUpdateInterview() {

        Set<QuestionsBlock> allQuestionsBlocks = new HashSet<>();
        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);

        Interview interviewExpected = new Interview();
        allQuestionsBlocks.add(questionsBlock);
        interviewExpected.setInterviewId("1");

        interviewExpected.setQuestionsBlocks(allQuestionsBlocks);
        interviewExpected.setType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        String interviewId = interviewDAO.putInterview(interviewExpected);
        Interview interviewActual = interviewDAO.getInterviewByAppointmentId(interviewId);
        assertEquals(interviewActual, interviewExpected);
        questionInformation.setQuestion("Question body2");
        Set<QuestionInformation> questionInformationsList2 = new HashSet<>();
        questionInformationsList2.add(questionInformation);
        QuestionsBlock questionsBlock2 = new QuestionsBlock();
        questionsBlock2.setQuestions(questionInformationsList2);
        Set<QuestionsBlock> allQuestionsBlocks2 = new HashSet<>();
        allQuestionsBlocks2.add(questionsBlock2);
        interviewExpected.setQuestionsBlocks(allQuestionsBlocks2);
        interviewDAO.updateInterview(interviewExpected);
        Interview interviewActual2 = interviewDAO.getInterviewByAppointmentId(interviewId);
        assertEquals(interviewActual2, interviewExpected);

    }

    @Test
    public void testRemoveInterviewByAppointmentId() {
        Set<QuestionsBlock> allQuestionsBlocks = new HashSet<>();
        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionsBlock.setQuestions(questionInformationsList);

        Interview interviewExpected = new Interview();
        allQuestionsBlocks.add(questionsBlock);
        String appointmentId = "1";
        interviewExpected.setInterviewId(appointmentId);

        interviewExpected.setQuestionsBlocks(allQuestionsBlocks);
        interviewExpected.setType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        interviewDAO.putInterview(interviewExpected);
        interviewDAO.removeInterviewByAppointmentId(appointmentId);

    }

    @Test
    public void testGetAllInterviewsId() {
        Interview interview1 = new Interview();
        interview1.setInterviewId("1");
        Interview interview2 = new Interview();
        interview2.setInterviewId("2");
        Interview interview3 = new Interview();
        interview3.setInterviewId("3");
        interviewDAO.putInterview(interview1);
        interviewDAO.putInterview(interview2);
        interviewDAO.putInterview(interview3);
        List<String> interviewIdList = interviewDAO.getAllInterviewsId();
        assertEquals(3, interviewIdList.size());

    }

}