package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.InterviewType;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import com.softserveinc.ita.interviewfactory.factory.InterviewFactory;
import com.softserveinc.ita.interviewfactory.service.interviewServices.InterviewService;
import com.softserveinc.ita.interviewfactory.service.questionsBlockServices.QuestionsBlockServices;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class QuestionsBlockServiceTests extends BaseServiceTests{

    @Autowired
    QuestionsBlockServices questionsBlockServices;

    @Autowired
    InterviewFactory interviewFactory;

    @Autowired
    InterviewService interviewService;

    @Test
    public void testAddQuestionsBlockAndExpectOk() throws WrongCriteriaException, HttpRequestException {
        Interview interviewBefore = interviewService.getInterviewByAppointmentID("1");
        Set<QuestionsBlock> allQuestionBlocksBefore = interviewBefore.getQuestionsBlocks();

        QuestionsBlock questionsBlock = new QuestionsBlock();
        questionsBlock.setInterviewId("1");
        questionsBlockServices.addQuestionsBlock(questionsBlock);
        Interview interview = interviewService.getInterviewByAppointmentID("1");
        Set<QuestionsBlock> allQuestionBlocksAfter = interview.getQuestionsBlocks();

        assertEquals(allQuestionBlocksAfter.size(), allQuestionBlocksBefore.size());

    }

    @Test
    public void testGetQuestionsBlockFromInterviewByUserIdAndExpectOk() throws WrongCriteriaException, HttpRequestException {
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
        questionsBlock.setInterviewId("1");
        questionsBlock.setUserId("1");
        questionsBlock.setId("1");

        QuestionsBlock questionsBlockActual = questionsBlockServices.getQuestionsBlockFromInterviewByUserId("1", "1");

        assertEquals(questionsBlockActual, questionsBlock);

    }
}
