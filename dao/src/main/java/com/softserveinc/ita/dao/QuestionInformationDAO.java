package com.softserveinc.ita.dao;


import com.softserveinc.ita.entity.QuestionInformation;

public interface QuestionInformationDAO {

    QuestionInformation getQuestionInformationById(String questionsInformationId);
    String updateQuestionInformation(QuestionInformation questionInformation);
    void deleteQuestionInformationById(String questionInformationId);

}
