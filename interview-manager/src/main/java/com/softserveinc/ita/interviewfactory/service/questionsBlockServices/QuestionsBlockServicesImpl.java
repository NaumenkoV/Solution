package com.softserveinc.ita.interviewfactory.service.questionsBlockServices;

import com.softserveinc.ita.dao.QuestionsBlockDAO;
import com.softserveinc.ita.entity.FinalComment;
import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import com.softserveinc.ita.interviewfactory.service.interviewServices.InterviewService;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class QuestionsBlockServicesImpl implements QuestionsBlockServices {

    @Autowired
    QuestionsBlockDAO questionsBlockDAO;

    @Autowired
    InterviewService interviewService;

    //Standard questions mock

    private QuestionsBlock standardQuestionsBlock = new QuestionsBlock();{
        QuestionInformation questionInformation1 = new QuestionInformation();
        questionInformation1.setQuestion("What is your name?");
        QuestionInformation questionInformation2 = new QuestionInformation();
        questionInformation2.setQuestion("How are you?");

        Set<QuestionInformation> QuestionInformationList = new HashSet<QuestionInformation>();{
            QuestionInformationList.add(questionInformation1);
            QuestionInformationList.add(questionInformation2);
        }

        standardQuestionsBlock.setQuestions(QuestionInformationList);
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public QuestionsBlock getQuestionsBlockFromInterviewByUserId(String userID, String appointmentId) throws WrongCriteriaException, HttpRequestException {
        interviewService.getInterviewByAppointmentID(appointmentId);
          return questionsBlockDAO.getQuestionsBlockByInterviewIdAndUserId(userID, appointmentId);
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public QuestionsBlock getQuestionsBlockByQuestionsBlockId(String questionsBlockId) {
        return questionsBlockDAO.getQuestionsBlockFromInterviewByQuestionsBlockId(questionsBlockId);
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addQuestionsBlock(QuestionsBlock questionsBlock) throws WrongCriteriaException, HttpRequestException {
        String interviewId = questionsBlock.getInterviewId();
        Interview interview = interviewService.getInterviewByAppointmentID(interviewId);
        interview.getQuestionsBlocks().add(questionsBlock);
        interviewService.updateInterview(interview);
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public String getQuestionsBlockIdByQuestionsBlockBody(QuestionsBlock questionsBlock, String userId)  {
        return questionsBlockDAO.getQuestionsBlockByInterviewIdAndUserId(userId, questionsBlock.getInterviewId()).getId();
    }

        @Override
 //       @Transactional(isolation = Isolation.READ_COMMITTED)
    public String updateQuestionsBlock(QuestionsBlock newQuestionsBlock, String userId) {
        newQuestionsBlock.setUserId(userId);
        return questionsBlockDAO.updateQuestionsBlock(newQuestionsBlock);
    }

    @Override
  //  @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateFinalCommentInQuestionsBlock(FinalComment finalComment, String userId) throws WrongCriteriaException, HttpRequestException {
        QuestionsBlock questionsBlock = getQuestionsBlockFromInterviewByUserId(userId, finalComment.getInterviewId());
        questionsBlock.setFinalComment(finalComment.getFinalComment());
        questionsBlock.setBonusPoints(finalComment.getBonusPoints());
        updateQuestionsBlock(questionsBlock, userId);
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteQuestionsBlockById(String questionsBlockId) {
        questionsBlockDAO.deleteQuestionsBlockById(questionsBlockId);
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public QuestionsBlock getStandardQuestionsBlockFromInterview(String interviewId) throws WrongCriteriaException, HttpRequestException {
        Set<QuestionsBlock> questionsBlocks = interviewService.getInterviewByAppointmentID(interviewId).getQuestionsBlocks();

        for (QuestionsBlock questionsBlock1 : questionsBlocks){
            if(questionsBlock1.getUserId() == null)
                return questionsBlock1;
        }
        return null;
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public QuestionsBlock getStandardQuestionsBlock() {
        return standardQuestionsBlock;
    }

    @Override
 //   @Transactional(isolation = Isolation.READ_COMMITTED)
    public void setStandardQuestionsBlock(QuestionsBlock standardQuestionsBlock) {
        this.standardQuestionsBlock = standardQuestionsBlock;
    }
}
