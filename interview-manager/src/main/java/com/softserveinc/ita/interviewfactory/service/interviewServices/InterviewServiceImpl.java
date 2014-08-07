package com.softserveinc.ita.interviewfactory.service.interviewServices;


import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.dao.InterviewDAO;
import com.softserveinc.ita.interviewfactory.factory.InterviewFactory;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    InterviewDAO interviewDAO;

    @Autowired
    InterviewFactory interviewFactory;

    @Autowired
    HttpRequestExecutor httpRequestExecutor;

    @Override
    public List<Interview> getInterviewByApplicantID(String applicantId) throws HttpRequestException, WrongCriteriaException {
        List<Interview> listForReturn = new ArrayList<>();
        Map<Class, String> httpApplicantParamMap = new HashMap<>();
        httpApplicantParamMap.put(Applicant.class, applicantId);
        List<String> listWithAppointmentsId = httpRequestExecutor.getListObjectsIdByPrams(Appointment.class, httpApplicantParamMap);

        for (String id : listWithAppointmentsId){
            listForReturn.add(getInterviewByAppointmentID(id));
        }
        return listForReturn;
    }

    @Override
    public String putInterview(String appointmentID, InterviewType type) throws HttpRequestException, WrongCriteriaException {

        if (type == null) throw new WrongCriteriaException("Type is wrong");
        else
        if (type.equals(InterviewType.INTERVIEW_WITHOUT_QUESTIONS)) {
            Interview interview =
                    interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS).create(appointmentID);
            return interviewDAO.putInterview(interview);
        }
        else
        if (type.equals(InterviewType.INTERVIEW_WITH_STANDARD_QUESTIONS)) {
            Interview interview =
                    interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_STANDARD_QUESTIONS).create(appointmentID);
            return interviewDAO.putInterview(interview);
        }
        else
        if (type.equals(InterviewType.INTERVIEW_WITH_USER_AND_STANDARD_QUESTIONS)) {
            Interview interview =
                    interviewFactory.getInterviewWithType(InterviewType.INTERVIEW_WITH_USER_AND_STANDARD_QUESTIONS).create(appointmentID);
            return interviewDAO.putInterview(interview);
        }
        else
        throw new WrongCriteriaException("Type is wrong");
    }

    @Override
    public Interview getInterviewByAppointmentID(String appointmentId) throws WrongCriteriaException, HttpRequestException {
        Interview interview = interviewDAO.getInterviewByAppointmentId(appointmentId);
        if (interview == null){
            String interviewId = putInterview(appointmentId, InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
            interview = interviewDAO.getInterviewByAppointmentId(interviewId);
        }
        return interview;
    }

    @Override
    public void removeInterviewByAppointmentId(String interviewId) throws HttpRequestException {
        interviewDAO.removeInterviewByAppointmentId(interviewId);
    }

    @Override
    public void updateInterview(Interview interview) throws HttpRequestException {
        interviewDAO.updateInterview(interview);
    }

    @Override
    public List<String> getAllInterviewsId() throws HttpRequestException {
        return interviewDAO.getAllInterviewsId();
    }

    @Override
    public List<Interview> getAllInterviews() throws HttpRequestException {
        List<String> allInterviewIdList = getAllInterviewsId();
        List<Interview> allInterviewsList = new ArrayList<>();
        for (String id : allInterviewIdList){
            allInterviewsList.add(interviewDAO.getInterviewByAppointmentId(id));
        }
        return allInterviewsList;
    }

    @Override
    public InterviewResults getInterviewResultsByInterviewId(String InterviewId) throws WrongCriteriaException, HttpRequestException {
        InterviewResults interviewResults = new InterviewResults();
        String finalComment = "";
        int totalPoints = 0;

        Set<QuestionsBlock> questionsBlocksSet = getInterviewByAppointmentID(InterviewId).getQuestionsBlocks();
        Iterator<QuestionsBlock> it = questionsBlocksSet.iterator();
        while(it.hasNext()){
            QuestionsBlock questionsBlock = it.next();
            Set<QuestionInformation> questionInformationSet = questionsBlock.getQuestions();
            Iterator<QuestionInformation> it2 = questionInformationSet.iterator();
            while(it2.hasNext()){
                QuestionInformation questionInformation = it2.next();
                totalPoints = totalPoints + questionInformation.getMark() * questionInformation.getWeight();

            }
            User user = httpRequestExecutor.getObjectByID(questionsBlock.getUserId(), User.class);
            String role = user.getRole().getName();
            finalComment = finalComment + role + ": " +  questionsBlock.getFinalComment() + ";\n";
            totalPoints = totalPoints + questionsBlock.getBonusPoints();
        }

        interviewResults.setFinalComment(finalComment);
        interviewResults.setTotalPoints(totalPoints);
        interviewResults.setInterviewId(InterviewId);
        return interviewResults;

    }
}
