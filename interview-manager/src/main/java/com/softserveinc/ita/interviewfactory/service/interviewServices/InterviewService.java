package com.softserveinc.ita.interviewfactory.service.interviewServices;

import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.InterviewResults;
import com.softserveinc.ita.entity.InterviewType;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;

import java.util.List;

public interface InterviewService {

    List<Interview> getInterviewByApplicantID(String ID) throws HttpRequestException, WrongCriteriaException;
    String putInterview(String appointmentID, InterviewType type) throws HttpRequestException, WrongCriteriaException;

    Interview getInterviewByAppointmentID(String interviewId) throws HttpRequestException, WrongCriteriaException;

    void removeInterviewByAppointmentId(String interviewId) throws HttpRequestException;
    void updateInterview(Interview interview) throws HttpRequestException;
    List<String> getAllInterviewsId() throws HttpRequestException;
    List<Interview> getAllInterviews() throws HttpRequestException;
    InterviewResults getInterviewResultsByInterviewId(String InterviewId) throws WrongCriteriaException, HttpRequestException;
}
