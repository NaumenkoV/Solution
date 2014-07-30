package com.softserveinc.ita.service.mocks;


import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.service.AppointmentService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
@Service
public class AppointmentServiceMock implements AppointmentService {

    public static final int TOMORROW = 24 * 60 * 60 * 1000;
    private LinkedList<Appointment> appointmentsList;

    {
        long currentTime = System.currentTimeMillis();

        String applicantId = "testApplicantId";
        String appointmentId = "testAppointmentId";

        List<String> users = new ArrayList<>();
        users.add("testUserId");
        Appointment appointment1 = new Appointment(users, applicantId, 1401866602L + TOMORROW);
        appointment1.setAppointmentId(appointmentId);

        List<String> users2 = new ArrayList<>();
        Appointment appointment2 = new Appointment(users, applicantId, 1401866603L + TOMORROW);
        appointment2.setAppointmentId(appointmentId);

        List<String> users3 = new ArrayList<>();
        Appointment appointment3 = new Appointment(users, applicantId, 1401866604L + TOMORROW);
        appointment3.setAppointmentId(appointmentId);


        Appointment todayFirstAppointment = new Appointment(users, applicantId, currentTime);
        todayFirstAppointment.setAppointmentId(appointmentId);
        Appointment todaySecondAppointment = new Appointment(users, applicantId, currentTime);
        todaySecondAppointment.setAppointmentId(appointmentId);

        appointmentsList = new LinkedList<>();
        Collections.addAll(appointmentsList, appointment1, appointment2, appointment3, todayFirstAppointment
                , todaySecondAppointment);
    }

    @Override
    public List<Appointment> getAppointmentByApplicantId(String applicantId) {
        List<Appointment> result = new ArrayList<>();
        if (applicantId.equals("testApplicantId")) {
            result.add(appointmentsList.get(0));
        } else {
            result.add(appointmentsList.get(2));
        }
        return result;
    }

    @Override
    public void removeAppointmentById(String appointmentId) {

    }

    @Override
    public Appointment getAppointmentByAppointmentId(String appointmentId) {
        if (appointmentId.equals("testAppointmentId")) {
            return appointmentsList.get(0);
        } else {
            return appointmentsList.get(2);
        }
    }

    @Override
    public String putAppointment(Appointment appointment) {
        return "testAppointmentId";
    }

    @Override
    public List<Appointment> getAppointmentsByDate(long date) {
        DateTime requirementDate = new DateTime(date);
        List<Appointment> resultList = new LinkedList<>();

        //if  required date less  than 1970 year then return empty result
        if (requirementDate.getMillis() < new DateTime(0).getMillis()) {
            return resultList;
        }

        for (Appointment appointment : appointmentsList) {

            DateTime appointmentDate = new DateTime(appointment.getStartTime());

            if (requirementDate.getYear() == appointmentDate.getYear() &&
                    requirementDate.getMonthOfYear() == appointmentDate.getMonthOfYear() &&
                    requirementDate.getDayOfMonth() == appointmentDate.getDayOfMonth()) {
                resultList.add(appointment);
            }
        }

        return resultList;

    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        List<Appointment> appointments = new ArrayList<>();
        int TOMORROW = 24 * 60 * 60 * 1000;
        List<String> users = new ArrayList<>();
        users.add("testUserId");
        appointments.add(new Appointment(users, "testApplicantId", 1401866602L + TOMORROW));
        Appointment appointment1 = appointment;
        appointments.remove(0);
        appointments.add(appointment1);
        if (!appointment1.equals(appointments.get(0))) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public String getAppointmentIdByGroupIdAndApplicantId(String groupId, String applicantId) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId("TestAppointmentId");
        appointment.setGroupId("TestGroupId");
        appointment.setApplicantId("TestApplicantId");
        if (appointment.getGroupId().equals(groupId) && appointment.getApplicantId().equals(applicantId)) {
            return appointment.getAppointmentId();
        } else {
            return "NotSuchAppointment";
        }
    }
}
