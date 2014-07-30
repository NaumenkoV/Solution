package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Appointment;

import java.util.List;

public interface AppointmentDAO {

    Appointment getAppointmentByAppointmentId(String appointmentId);

    String putAppointment(Appointment appointment);

    List<Appointment> getAppointmentByApplicantId(String applicantId);

    void removeAppointmentById(String appointmentId);

    Appointment updateAppointment(Appointment appointment);

    String getAppointmentIdByGroupIdAndApplicantId(String groupId, String applicantId);

    List<Appointment> getAppointmentsByDate(long startOfDay, long endOfDay);
}
