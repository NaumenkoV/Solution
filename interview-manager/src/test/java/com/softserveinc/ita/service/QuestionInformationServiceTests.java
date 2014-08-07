package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import com.softserveinc.ita.interviewfactory.dao.mock.InterviewDAOMock;
import com.softserveinc.ita.interviewfactory.dao.mock.QuestionsBlockDAOMock;
import com.softserveinc.ita.interviewfactory.service.questionInformationServices.QuestionsInformationServices;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import exceptions.WrongCriteriaException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class QuestionInformationServiceTests extends BaseServiceTests {

    @Autowired
    QuestionsInformationServices questionsInformationServices;

    @Autowired
    private JsonUtil interviewUtilJson;

    @Test
    public void testAddQuestionInformationAndExpectOk() throws WrongCriteriaException, HttpRequestException {
        QuestionInformation questionInformation = new QuestionInformation();
        questionInformation.setQuestion("Question body");
        questionInformation.setAnswer("answer");
        questionInformation.setComment("normas");
        questionInformation.setMark(2);
        questionInformation.setWeight(1);
        questionInformation.setInterviewId("1");
        questionsInformationServices.addQuestionInformation(questionInformation, "1");
        Interview interview = InterviewDAOMock.interviewsList.get(0);
        Set<QuestionsBlock> allQuestionsBlocksActual = interview.getQuestionsBlocks();
        Iterator<QuestionsBlock> it = allQuestionsBlocksActual.iterator();
        QuestionsBlock questionsBlockActual = it.next();

        Set<QuestionInformation> questionInformationSet = questionsBlockActual.getQuestions();
        Iterator<QuestionInformation> it2 = questionInformationSet.iterator();
        QuestionInformation questionInformationActual = it2.next();

        assertEquals(questionInformationActual, questionInformation);


    }

}
