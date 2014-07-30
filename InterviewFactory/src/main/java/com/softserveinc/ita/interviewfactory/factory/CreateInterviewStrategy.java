package com.softserveinc.ita.interviewfactory.factory;

import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.service.exception.HttpRequestException;


public interface CreateInterviewStrategy {

    public Interview create(String interviewId) throws HttpRequestException;
}
