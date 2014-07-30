package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.User;

import java.util.List;

public interface InterviewDAO {

    Interview getInterviewByAppointmentId(String appointmentId);

    String putInterview(Interview interview);

    void removeInterviewByAppointmentId(String appointmentId);

    void updateInterview(Interview interview);
    List<String> getAllInterviewsId();
    List<Interview> getAllInterviews();

}
