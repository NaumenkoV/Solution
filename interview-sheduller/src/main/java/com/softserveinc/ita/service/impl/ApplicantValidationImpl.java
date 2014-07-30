package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.service.ApplicantValidation;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicantValidationImpl implements ApplicantValidation {
    @Autowired
    private HttpRequestExecutor httpRequestExecutor;

    @Override
    public boolean applicantExists(String applicantId) {
        try {
            httpRequestExecutor.getObjectByID(applicantId, Applicant.class);
            return true;
        }
        catch (HttpRequestException ex) {
            return false;
        }
    }
}