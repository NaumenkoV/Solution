package com.softserveinc.ita.interviewfactory.factory;

import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("INTERVIEW_WITHOUT_QUESTIONS")
public class InterviewWithoutQuestions implements CreateInterviewStrategy {

    @Autowired
    HttpRequestExecutor httpRequestExecutor;

    @Override
    public Interview create(String interviewId) throws HttpRequestException {

        Interview interview = new Interview(interviewId);
        Set<QuestionsBlock> allQuestionsBlocks = new HashSet<>();
        Appointment appointment = httpRequestExecutor.getObjectByID(interviewId, Appointment.class);

        List<String> Users = appointment.getUserIdList();

        for (int i = 0; i < Users.size(); i++){
            QuestionsBlock userQuestionsBlock = new QuestionsBlock(Users.get(i));
            User user = httpRequestExecutor.getObjectByID(Users.get(i), User.class);
            String role = user.getRole().getName();
            userQuestionsBlock.setUserRole(role);
            Set<QuestionInformation> questionInformationSet = new HashSet<>();
            userQuestionsBlock.setQuestions(questionInformationSet);
            userQuestionsBlock.setInterviewId(interviewId);
            allQuestionsBlocks.add(userQuestionsBlock);
        }
        interview.setQuestionsBlocks(allQuestionsBlocks);
        interview.setType(InterviewType.INTERVIEW_WITHOUT_QUESTIONS);
        interview.setInterviewId(appointment.getAppointmentId());
        return interview;
    }
}
