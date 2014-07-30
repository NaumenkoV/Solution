package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.dao.AppointmentDAO;
import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.service.AppointmentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentDAO appointmentDAO;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String putAppointment(Appointment appointment) {
        return appointmentDAO.putAppointment(appointment);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Appointment> getAppointmentByApplicantId(String applicantId) {
        return appointmentDAO.getAppointmentByApplicantId(applicantId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void removeAppointmentById(String appointmentId) {
        appointmentDAO.removeAppointmentById(appointmentId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Appointment getAppointmentByAppointmentId(String appointmentId) {
        return appointmentDAO.getAppointmentByAppointmentId(appointmentId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Appointment> getAppointmentsByDate(long date) {
        DateTime dateTime = new DateTime(date);
        DateTime startOfDay = dateTime.withTime(0, 0, 0, 0);
        DateTime endOfDay = dateTime.withTime(23, 59, 59, 999);
        return appointmentDAO.getAppointmentsByDate(startOfDay.getMillis(), endOfDay.getMillis());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentDAO.updateAppointment(appointment);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String getAppointmentIdByGroupIdAndApplicantId(String groupId, String applicantId) {
        return appointmentDAO.getAppointmentIdByGroupIdAndApplicantId(groupId,applicantId);
    }
}
