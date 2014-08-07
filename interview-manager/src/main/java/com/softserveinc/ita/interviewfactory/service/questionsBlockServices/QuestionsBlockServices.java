package com.softserveinc.ita.interviewfactory.service.questionsBlockServices;

import com.softserveinc.ita.entity.FinalComment;
import com.softserveinc.ita.entity.QuestionsBlock;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;

public interface QuestionsBlockServices {

    QuestionsBlock getQuestionsBlockFromInterviewByUserId(String userID, String appointmentId) throws WrongCriteriaException, HttpRequestException;
    QuestionsBlock getQuestionsBlockByQuestionsBlockId(String questionsBlockId);
    void addQuestionsBlock(QuestionsBlock questionsBlock) throws WrongCriteriaException, HttpRequestException;
    String updateQuestionsBlock(QuestionsBlock newQuestionsBlock, String userId);
    void deleteQuestionsBlockById(String questionsBlockId);
    String getQuestionsBlockIdByQuestionsBlockBody(QuestionsBlock questionsBlock, String userId);
    QuestionsBlock getStandardQuestionsBlockFromInterview(String interviewId) throws WrongCriteriaException, HttpRequestException;
    QuestionsBlock getStandardQuestionsBlock();
    void setStandardQuestionsBlock(QuestionsBlock standardQuestionsBlock);
    void updateFinalCommentInQuestionsBlock(FinalComment finalComment, String userId) throws WrongCriteriaException, HttpRequestException;
}
