package com.softserveinc.ita.interviewfactory.service.questionInformationServices;

import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;

import java.util.List;

public interface QuestionsInformationServices {

    QuestionInformation getQuestionInformationById(String questionInformationId);
    void addQuestionInformation(QuestionInformation questionInformation, String userId) throws WrongCriteriaException, HttpRequestException;
    void deleteQuestionInformationById(String questionInformationId);
    String updateQuestionInformation(QuestionInformation questionInformation);

    String getQuestionInformationIdByQuestionInformationBody(QuestionInformation questionInformation, String userId) throws WrongCriteriaException, HttpRequestException;
}
