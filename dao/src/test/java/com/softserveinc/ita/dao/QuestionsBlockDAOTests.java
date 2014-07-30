package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.*;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

import static junit.framework.Assert.assertEquals;

public class QuestionsBlockDAOTests extends BaseDAOTest {

    @Autowired
    private QuestionsBlockDAO questionsBlockDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private InterviewDAO interviewDAO;

    @Test
    public void testGetQuestionsBlockFromInterviewByQuestionsBlockId()  {

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
        Set<QuestionsBlock> allQuestionsBlocksActual = interviewActual.getQuestionsBlocks();
        Iterator<QuestionsBlock> it = allQuestionsBlocksActual.iterator();
        QuestionsBlock questionsBlockActual = it.next();
        String questionsBlockId = questionsBlockActual.getId();

        QuestionsBlock questionsBlockExpected = questionsBlockDAO.getQuestionsBlockFromInterviewByQuestionsBlockId(questionsBlockId);
        assertEquals(questionsBlockExpected, questionsBlockActual);

    }

    @Test
    public void testUpdateQuestionsBlockFromInterviewByQuestionsBlockId()  {

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
        Set<QuestionsBlock> allQuestionsBlocksActual = interviewActual.getQuestionsBlocks();
        Iterator<QuestionsBlock> it = allQuestionsBlocksActual.iterator();
        QuestionsBlock questionsBlockActual = it.next();
        questionsBlockActual.setUserId("3");
        String questionsBlockId = questionsBlockActual.getId();

        String questionsBlockExpectedId = questionsBlockDAO.updateQuestionsBlock(questionsBlockActual);
        assertEquals(questionsBlockExpectedId, questionsBlockId);

    }

    @Test
    public void testGetQuestionsBlockFromInterviewByUserId()  {

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
        questionsBlock.setUserId("2");
        questionsBlock.setInterviewId("1");
        questionsBlock.setBonusPoints(12);
        questionsBlock.setFinalComment("wefwef");

        QuestionsBlock questionsBlock2 = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList2 = new HashSet<>();
        QuestionInformation questionInformation2 = new QuestionInformation();
        questionInformation2.setQuestion("Question body2");
        questionInformation2.setAnswer("answer2");
        questionInformation2.setComment("normas2");
        questionInformation2.setMark(2);
        questionInformation2.setWeight(1);
        questionInformation2.setInterviewId("1");
        questionInformationsList2.add(questionInformation2);
        questionsBlock2.setQuestions(questionInformationsList2);
        questionsBlock2.setUserId("1");
        questionsBlock2.setInterviewId("1");
        questionsBlock2.setBonusPoints(12);
        questionsBlock2.setFinalComment("wefwef");

        Interview interviewExpected = new Interview();
        allQuestionsBlocks.add(questionsBlock);
        allQuestionsBlocks.add(questionsBlock2);
        interviewExpected.setInterviewId("1");

        interviewExpected.setQuestionsBlocks(allQuestionsBlocks);
        interviewExpected.setType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        String interviewId = (String) sessionFactory.getCurrentSession().save(interviewExpected);
        Interview interviewActual = interviewDAO.getInterviewByAppointmentId(interviewId);
        Set<QuestionsBlock> allQuestionsBlocksActual = interviewActual.getQuestionsBlocks();
        Iterator<QuestionsBlock> it = allQuestionsBlocksActual.iterator();
        QuestionsBlock questionsBlockActual = it.next();
        String questionsBlockUserId = questionsBlockActual.getUserId();

        QuestionsBlock questionsBlockExpected = questionsBlockDAO.getQuestionsBlockByInterviewIdAndUserId(questionsBlockUserId, interviewId);
        assertEquals(questionsBlockExpected, questionsBlockActual);

    }

}