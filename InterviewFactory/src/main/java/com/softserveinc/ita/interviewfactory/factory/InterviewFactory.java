package com.softserveinc.ita.interviewfactory.factory;


import com.softserveinc.ita.entity.InterviewType;


public interface InterviewFactory {

    CreateInterviewStrategy getInterviewWithType(InterviewType type);
}
