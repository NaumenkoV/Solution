package com.softserveinc.ita.interviewfactory.service.questionInformationServices;

import com.softserveinc.ita.dao.QuestionInformationDAO;
import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import com.softserveinc.ita.interviewfactory.service.interviewServices.InterviewService;
import com.softserveinc.ita.interviewfactory.service.questionsBlockServices.QuestionsBlockServices;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import exceptions.WrongCriteriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Set;
@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class QuestionsInformationServiceImpl implements QuestionsInformationServices {

    @Autowired
    QuestionInformationDAO questionInformationDAO;

    @Autowired
    QuestionsBlockServices questionsBlockService;

    @Autowired
    InterviewService interviewService;

    @Override
    public QuestionInformation getQuestionInformationById(String questionsInformationId) {
        return questionInformationDAO.getQuestionInformationById(questionsInformationId);
    }


    @Override
  //  @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addQuestionInformation(QuestionInformation questionInformation, String userId) throws WrongCriteriaException, HttpRequestException {
        String interviewId = questionInformation.getInterviewId();
        Interview interview = interviewService.getInterviewByAppointmentID(interviewId); //если интервью не существует, тут оно будет создано
        Set<QuestionsBlock> allQuestionsBlocks = interview.getQuestionsBlocks();

        QuestionsBlock questionsBlock = new QuestionsBlock();
        Iterator<QuestionsBlock> it = allQuestionsBlocks.iterator();
        while(it.hasNext()){
            QuestionsBlock questionsBlockActual = it.next();
            if (questionsBlockActual.getUserId().equals(userId)){
                questionsBlock = questionsBlockActual;

                Set<QuestionInformation> questionInformationSet = questionsBlock.getQuestions();
                questionInformationSet.add(questionInformation);
                questionsBlock.setQuestions(questionInformationSet);
                it.remove();
            }
        }

        allQuestionsBlocks.add(questionsBlock);
        interview.setQuestionsBlocks(allQuestionsBlocks);
        interviewService.updateInterview(interview);
    }

    @Override
  //  @Transactional(isolation = Isolation.READ_COMMITTED)
    public String getQuestionInformationIdByQuestionInformationBody(QuestionInformation questionInformation, String userId) throws WrongCriteriaException, HttpRequestException {

        QuestionsBlock questionsBlock = questionsBlockService.getQuestionsBlockFromInterviewByUserId(userId, questionInformation.getInterviewId());

        Set<QuestionInformation> questionInformationSet = questionsBlock.getQuestions();
        Iterator<QuestionInformation> it3 = questionInformationSet.iterator();

        while(it3.hasNext()){
            QuestionInformation questionInformation1 = it3.next();
            if (questionInformation1.getQuestion().equals(questionInformation.getQuestion())) {
                return questionInformation1.getId();
            }
        }
        return null;
    }

    @Override
  //  @Transactional(isolation = Isolation.READ_COMMITTED)
    public String updateQuestionInformation(QuestionInformation questionInformation) {
        return questionInformationDAO.updateQuestionInformation(questionInformation);
    }

    @Override
  //  @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteQuestionInformationById(String questionInformationId) {
        questionInformationDAO.deleteQuestionInformationById(questionInformationId);
    }
}
