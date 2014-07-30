package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.InterviewType;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class QuestionInformationDAOTests extends BaseDAOTest {

    @Autowired
    private QuestionInformationDAO questionInformationDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private InterviewDAO interviewDAO;

    @Test
    public void testGetQuestionInformationById()  {

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

        Set<QuestionInformation> questionInformationSet = questionsBlockActual.getQuestions();
        Iterator<QuestionInformation> it2 = questionInformationSet.iterator();
        QuestionInformation questionInformationActual = it2.next();
        String questionInformationId = questionInformationActual.getId();

        QuestionInformation questionInformationExpected = questionInformationDAO.getQuestionInformationById(questionInformationId);
        assertEquals(questionInformationExpected,questionInformationActual);

    }

    @Test
    public void testUpdateQuestionInformationById()  {

        Set<QuestionsBlock> allQuestionsBlocks = new HashSet<>();
        QuestionsBlock questionsBlock = new QuestionsBlock();
        Set<QuestionInformation> questionInformationsList = new HashSet<>();
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("Odin otvet");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");

        QuestionInformation questionInformation2 = new QuestionInformation();
        questionInformation2.setQuestion("Question body2");
        questionInformation2.setAnswer("answer");
        questionInformation2.setComment("normas");
        questionInformation2.setMark(2);
        questionInformation2.setWeight(1);
        questionInformation2.setInterviewId("1");
        questionInformationsList.add(questionInformation);
        questionInformationsList.add(questionInformation2);
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

        Set<QuestionInformation> questionInformationSet = questionsBlockActual.getQuestions();
        Iterator<QuestionInformation> it2 = questionInformationSet.iterator();
        QuestionInformation questionInformationActual = it2.next();
        questionInformationActual.setAnswer("Drugoi otvet");

        questionInformationDAO.updateQuestionInformation(questionInformationActual);

        Interview interviewActual2 = interviewDAO.getInterviewByAppointmentId(interviewId);
        Set<QuestionsBlock> allQuestionsBlocksActual2 = interviewActual2.getQuestionsBlocks();
        Iterator<QuestionsBlock> it3 = allQuestionsBlocksActual2.iterator();
        QuestionsBlock questionsBlockActual2 = it3.next();

        Set<QuestionInformation> questionInformationSet2 = questionsBlockActual2.getQuestions();
        Iterator<QuestionInformation> it4 = questionInformationSet2.iterator();
        QuestionInformation questionInformationExpected = it4.next();
        assertEquals(questionInformationActual, questionInformationExpected);

    }

}
