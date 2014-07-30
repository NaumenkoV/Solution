package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.MailService;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.utils.JsonUtil;
import org.apache.camel.Consume;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    public static final String ENCODING = "UTF-8";
    public static final String DATE_FORMAT = "dd/MM/yyyy hh:mm";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String FROM = "javasendertest@gmail.com";
    public static final String LOGO = "ItAcademyLogo";
    public static final String LOGO_IMAGE_REF = "images/joinProfessionals.png";
    public static final String TIME = "time";
    public static final String COURSE = "course";
    public static final String GROUP_START_TIME = "groupStartTime";
    public static final String HR_NAME = "HRName";
    public static final String HR_SURNAME = "HRSurname";
    public static final String HR_PHONE = "HRPhone";
    public static final String HR_EMAIL = "HRemail";
    public static final String COURSE_ADDRESS = "courseAddress";
    public static final String APPLICANT_EMAIL = "applicantEmail";
    public static final String GROUP_NAME = "groupName";


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private HttpRequestExecutor httpRequestExecutor;

    @Autowired
    private JsonUtil jsonUtil;

    private MimeMessageHelper helper;
    private MimeMessage mimeMessage;

    @Consume(uri = "activemq:notification.queue")
    public void notifyApplicant(String notificationJSONInfo) throws HttpRequestException {
        NotificationJSONInfo notificationInfo = jsonUtil.fromJson(notificationJSONInfo, NotificationJSONInfo.class);
        mimeMessage = mailSender.createMimeMessage();
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String applicantId = notificationInfo.getApplicantId();
        String groupId =  notificationInfo.getGroupId();
        String responsibleHrId =  notificationInfo.getResponsibleHrId();
        Applicant applicant = null;
        Group group = null;
        User responsibleHr = null;
//        try {
            applicant = httpRequestExecutor.getObjectByID(applicantId, Applicant.class);

            group = httpRequestExecutor.getObjectByID(groupId, Group.class);
            responsibleHr = httpRequestExecutor.getObjectByID(responsibleHrId, User.class);

            Applicant.Status status = group.getApplicants().get(applicantId).getStatus();
            switch (status) {
                case NOT_SCHEDULED:
                    sendNotScheduledLetterModel(applicant, group, responsibleHr);
                    break;
                case SCHEDULED:
                    sendScheduledLetter(applicant, group, responsibleHr);
                    break;
                case NOT_PASSED:
                    sendNotPassedLetter(applicant, group);
                    break;
                case PASSED:
                    sendPassedLetter(applicant, group, responsibleHr);
                    break;
                case EMPLOYED:
                    sendEmployedLetter(applicant, group, responsibleHr);
            }
//        } catch (HttpRequestException e) {
//            e.printStackTrace();
//        }
    }

    private void sendEmployedLetter(Applicant applicant, Group group, User responsibleHr) {
        Map<String, Object> model = new HashMap<>();
        model.put(NAME, applicant.getName());
        model.put(SURNAME, applicant.getSurname());
        model.put(HR_NAME, responsibleHr.getName());
        model.put(HR_SURNAME, responsibleHr.getSurname());
        model.put(HR_PHONE, responsibleHr.getPhone());
        model.put(HR_EMAIL, responsibleHr.getEmail());
        model.put(APPLICANT_EMAIL, applicant.getEmail());
        sendLetter(group.getApplicants().get(applicant.getId()).getStatus(), model);
    }

    private void sendNotScheduledLetterModel(Applicant applicant, Group group, User responsibleHr) {
        Map<String, Object> model = new HashMap<>();
        model.put(NAME, applicant.getName());
        model.put(SURNAME, applicant.getSurname());
        model.put(COURSE, group.getCourse().getName());
        model.put(GROUP_NAME, group.getGroupName());
        model.put(HR_NAME, responsibleHr.getName());
        model.put(HR_SURNAME, responsibleHr.getSurname());
        model.put(HR_PHONE, responsibleHr.getPhone());
        model.put(HR_EMAIL, responsibleHr.getEmail());
        model.put(APPLICANT_EMAIL, applicant.getEmail());
        sendLetter(group.getApplicants().get(applicant.getId()).getStatus(), model);
    }

    private void sendPassedLetter(Applicant applicant, Group group, User responsibleHr) {
        Map<String, Object> model = new HashMap<>();
        model.put(NAME, applicant.getName());
        model.put(SURNAME, applicant.getSurname());
        model.put(COURSE, group.getCourse().getName());
        model.put(COURSE_ADDRESS, group.getAddress());
        model.put(GROUP_START_TIME, convertTimeToDate(group.getStartTime()));
        model.put(HR_NAME, responsibleHr.getName());
        model.put(HR_SURNAME, responsibleHr.getSurname());
        model.put(HR_PHONE, responsibleHr.getPhone());
        model.put(HR_EMAIL, responsibleHr.getEmail());
        model.put(APPLICANT_EMAIL, applicant.getEmail());
        sendLetter(group.getApplicants().get(applicant.getId()).getStatus(), model);
    }

    private void sendNotPassedLetter(Applicant applicant, Group group) {
        Map<String, Object> model = new HashMap<>();
        model.put(NAME, applicant.getName());
        model.put(SURNAME, applicant.getSurname());
        model.put(APPLICANT_EMAIL, applicant.getEmail());
        sendLetter(group.getApplicants().get(applicant.getId()).getStatus(), model);
    }


    private void sendScheduledLetter(Applicant applicant, Group group, User responsibleHr) throws HttpRequestException {
        Appointment appointment = null;
        String appointmentID;
//        try {
            HashMap<Class, String> groupAndApplicantIDs = new HashMap<>();
            groupAndApplicantIDs.put(Applicant.class, applicant.getId());
            groupAndApplicantIDs.put(Group.class, group.getGroupID());
            appointmentID = httpRequestExecutor.getListObjectsIdByPrams(Appointment.class, groupAndApplicantIDs).get(0);
            appointment = httpRequestExecutor.getObjectByID(appointmentID, Appointment.class);
//        } catch (HttpRequestException e) {
//            e.printStackTrace();
//        }
        Map<String, Object> model = new HashMap<>();
        model.put(NAME, applicant.getName());
        model.put(SURNAME, applicant.getSurname());
        model.put(COURSE_ADDRESS, group.getAddress());
        model.put(TIME, convertTimeToDate(appointment.getStartTime()));
        model.put(HR_NAME, responsibleHr.getName());
        model.put(HR_SURNAME, responsibleHr.getSurname());
        model.put(HR_PHONE, responsibleHr.getPhone());
        model.put(HR_EMAIL, responsibleHr.getEmail());
        model.put(APPLICANT_EMAIL, applicant.getEmail());
        sendLetter(group.getApplicants().get(applicant.getId()).getStatus(), model);
    }

    private void sendLetter(Applicant.Status applicantStatus, Map<String, Object> letterModel) {
        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                applicantStatus.getTemplateRef(), ENCODING, letterModel);
        try {
            helper.setFrom(FROM);
            helper.setTo((String) letterModel.get(APPLICANT_EMAIL));
            helper.setText(emailText, true);
            helper.setSubject(applicantStatus.getSubject());
            ClassPathResource image = new ClassPathResource(LOGO_IMAGE_REF);
            helper.addInline(LOGO, image);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String convertTimeToDate(long milliseconds) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }
}