package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;

public interface QuestionsBlockDAO {

    QuestionsBlock getQuestionsBlockByInterviewIdAndUserId(String userID, String appointmentId);
    QuestionsBlock getQuestionsBlockFromInterviewByQuestionsBlockId(String questionsBlockId);
    String updateQuestionsBlock(QuestionsBlock newQuestionsBlock);
    void deleteQuestionsBlockById(String questionsBlockId);
    QuestionsBlock getStandardQuestionsBlockFromInterview(String interviewId);
    QuestionsBlock getStandardQuestionsBlock();
    void setStandardQuestionsBlock(QuestionsBlock standardQuestionsBlock);
}
