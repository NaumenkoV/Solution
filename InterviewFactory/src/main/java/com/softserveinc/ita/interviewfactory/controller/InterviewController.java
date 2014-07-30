package com.softserveinc.ita.interviewfactory.controller;


import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.interviewfactory.service.interviewServices.InterviewService;
import com.softserveinc.ita.interviewfactory.service.questionInformationServices.QuestionsInformationServices;
import com.softserveinc.ita.interviewfactory.service.questionsBlockServices.QuestionsBlockServices;
import com.softserveinc.ita.service.exception.HttpRequestException;
import exceptions.WrongCriteriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class InterviewController {

    @Autowired
    InterviewService interviewService;
    @Autowired
    QuestionsBlockServices questionsBlockServices;
    @Autowired
    QuestionsInformationServices questionsInformationServices;

    @RequestMapping(value = "applicants/{applicantId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public List<Interview> getInterviewByApplicantId(@PathVariable("applicantId") String applicantId) throws WrongCriteriaException, HttpRequestException {
        return interviewService.getInterviewByApplicantID(applicantId);
    }

    @RequestMapping(value = "{interviewId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    public Interview getInterviewByAppointmentId(@PathVariable("interviewId") String interviewId)
            throws WrongCriteriaException, HttpRequestException {
        return interviewService.getInterviewByAppointmentID(interviewId);
    }

    @RequestMapping(value = "{interviewId}/result", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public InterviewResults getInterviewResultsByInterviewId(@PathVariable("interviewId") String interviewId)
            throws WrongCriteriaException, HttpRequestException {
        return interviewService.getInterviewResultsByInterviewId(interviewId);
    }

    @RequestMapping(value = "{interviewId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeInterviewByAppointmentId(@PathVariable("interviewId") String interviewId) throws HttpRequestException {
        interviewService.removeInterviewByAppointmentId(interviewId);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInterviewByAppointmentId(@RequestBody Interview interview) throws HttpRequestException {
        interviewService.updateInterview(interview);
    }

    @RequestMapping(value = "interviewing/answer", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public QuestionInformation addQuestionInformation(@RequestBody QuestionInformation questionInformation,
                                         @CookieValue("userId") String userId,
                                         @CookieValue("appointmentId") String appointmentId)
            throws WrongCriteriaException, HttpRequestException {
        questionInformation.setInterviewId(appointmentId);
        interviewService.getInterviewByAppointmentID(appointmentId);
        questionsInformationServices.addQuestionInformation(questionInformation, userId);
        questionsInformationServices.getQuestionInformationIdByQuestionInformationBody(questionInformation, userId);

        return new QuestionInformation();
    }

    @RequestMapping(value = "interviewing/final_comment", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateFinalCommentInQuestionsBlock(@RequestBody FinalComment finalComment,
                                                   @CookieValue("userId") String userId,
                                                   @CookieValue("appointmentId") String appointmentId) throws WrongCriteriaException, HttpRequestException {
        finalComment.setInterviewId(appointmentId);
        interviewService.getInterviewByAppointmentID(appointmentId);
        questionsBlockServices.updateFinalCommentInQuestionsBlock(finalComment, userId);
    }

    @RequestMapping(value = "interviewing/answer", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String updateQuestionInformation(@RequestBody QuestionInformation questionInformation,
                                            @CookieValue("appointmentId") String appointmentId){
        questionInformation.setInterviewId(appointmentId);
        return questionsInformationServices.updateQuestionInformation(questionInformation);
    }

    @RequestMapping(value = "interviewing/answer", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestionInformationById(@RequestBody String questionsInformationId) {

        questionsInformationServices.deleteQuestionInformationById(questionsInformationId);
    }
}

