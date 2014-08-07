package com.softserveinc.ita.interviewfactory;

import com.softserveinc.ita.entity.*;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.exception.HttpRequestException;
import com.softserveinc.ita.service.impl.AbstractHttpRequestExecutorRestImpl;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class HttpRequestExecutorFactoryMock extends AbstractHttpRequestExecutorRestImpl{

    //-------------VadimNaumenko mock for tests------------------------------------

    private long startTime = 1403308782454L;
    public static final int TOMORROW = 24 * 60 * 60 * 1000;

    Appointment appointment1;
    Appointment appointment2;
    Appointment appointment3;
    Appointment appointment4;
    Appointment appointment5;

    private User user1 = new User();
    private User user2 = new User();
    private User user3 = new User();
    private Role role1= new Role();
    private Role role2= new Role();
    private Role role3= new Role();

    private Applicant applicant1 = new Applicant();
    private Applicant applicant2 = new Applicant();
    private Applicant applicant3 = new Applicant();

    List<User> usersList = new ArrayList<User>(); {

        Question question1 = new Question("Have you ever were connected with quality assurance engineering?", 2);
        Question question2 = new Question("Have you ever were connected with database developing?", 3);
        Question question3 = new Question("Tell me something about JUnit testing.", 2);
        Question question4 = new Question("Your last book you read?", 3);
        Question question5 = new Question("Where did you study?", 2);
        Question question6 = new Question("Are you married?", 3);
        List<Question> questionsList1 = new ArrayList<>();
        Collections.addAll(questionsList1, question1, question2);
        role1.setName("IT Project Manager");
        role2.setName("Software Developer");
        role3.setName("HR Manager");
        user1.setId("1");
        user1.setRole(role1);
        user2.setId("2");
        user2.setRole(role2);
        user3.setId("3");
        user3.setRole(role3);
        user1.setQuestions(questionsList1);
        List<Question> questionsList2 = new ArrayList<>();
        Collections.addAll(questionsList2, question3, question4);
        user2.setQuestions(questionsList1);
        List<Question> questionsList3 = new ArrayList<>();
        Collections.addAll(questionsList3, question5, question6);
        user3.setQuestions(questionsList1);
        Collections.addAll(usersList, user1, user2, user3);
    }

    List<String> usersIdList = new ArrayList<String>(); {
        Collections.addAll(usersIdList, user1.getId(), user2.getId(), user3.getId());
    }


    List<String> usersIdList2 = new ArrayList<String>(); {
        Collections.addAll(usersIdList2, user1.getId());
    }

    List<Appointment> appointmentList = new ArrayList<>();{
        applicant1.setId("1");
        appointment1 = new Appointment(usersIdList2, applicant1.getId(), startTime);
        appointment1.setAppointmentId("1");
        applicant2.setId("2");
        applicant3.setId("3");
        appointment2 = new Appointment(usersIdList, applicant2.getId(), startTime + TOMORROW);
        appointment2.setAppointmentId("2");
        appointment3 = new Appointment(usersIdList, applicant2.getId(), startTime + TOMORROW);
        appointment3.setAppointmentId("3");
        appointment4 = new Appointment(usersIdList, applicant2.getId(), startTime + TOMORROW);
        appointment4.setAppointmentId("4");
        appointment5 = new Appointment(usersIdList2, applicant3.getId(), startTime + TOMORROW);
        appointment5.setAppointmentId("5");
        Collections.addAll(appointmentList, appointment1, appointment2, appointment3, appointment4, appointment5);
    }

    List<String> appointmentIdList1 = new ArrayList<>();{
        Collections.addAll(appointmentIdList1, appointment5.getAppointmentId());
    }

    //-------------VadimNaumenko mock from tests

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public HttpRequestExecutorFactoryMock (String baseUrl) {
        super(baseUrl);
        this.baseUrl = baseUrl;
        this.restTemplate  = new RestTemplate();
    }


    @Override
    public <T> T getObjectByID(String id, Class<T> objectClass) throws HttpRequestException {

        // name subdirectory
        String subclass = objectClass.getSimpleName().toLowerCase();

        if (subclass.equals("appointment")){
            for (Appointment appointment : appointmentList){
                if (appointment.getAppointmentId().equals(id)) return (T) appointment;
            }
            throw new HttpRequestException("Appointment not found");
        }
        else if(subclass.equals("user")){
            for (User user : usersList) {
                if (user.getId().equals(id)) {
                    return (T) user;
                }

            }
            throw new HttpRequestException("User not found");
        }
        throw new HttpRequestException("Wrong object");
    }

    @Override
    public List<String> getAllObjectsID(Class objectClass) throws HttpRequestException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getListObjectsIdByPrams(Class objectClass, Map<Class, String> urlValues) throws HttpRequestException {
        return appointment5.getAppointmentId();
    }
}
