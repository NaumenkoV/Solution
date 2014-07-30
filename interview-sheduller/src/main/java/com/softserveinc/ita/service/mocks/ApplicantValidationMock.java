package com.softserveinc.ita.service.mocks;

import com.softserveinc.ita.service.ApplicantValidation;
import org.springframework.stereotype.Service;

@Service
public class ApplicantValidationMock implements ApplicantValidation {
    public boolean applicantExists(String applicantId) {
        return applicantId.equals("testApplicantId");
    }
}