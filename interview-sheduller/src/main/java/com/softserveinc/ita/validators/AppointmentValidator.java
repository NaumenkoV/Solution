package com.softserveinc.ita.validators;

import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.service.ApplicantValidation;
import com.softserveinc.ita.service.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class AppointmentValidator implements Validator {
    @Autowired
    UserValidation userService;
    @Autowired
    ApplicantValidation applicantValidation;

    @Override
    public boolean supports(Class<?> aClass) {
        return Appointment.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Appointment appointment = (Appointment) o;

        validateUsers(appointment, errors);
        validateApplicant(appointment, errors);
    }

    private void validateUsers(Appointment appointment, Errors errors) {
        List<String> userIdList = appointment.getUserIdList();
        for (String userId : userIdList) {
            if (!userService.userExists(userId)) {
                errors.reject("400", "There is no user with id " + userId);
            }
        }
    }


    private void validateApplicant(Appointment appointment, Errors errors) {
        if (!applicantValidation.applicantExists(appointment.getApplicantId())) {
            errors.reject("400", "There is no applicant with id " + appointment.getApplicantId());
        }
    }
}