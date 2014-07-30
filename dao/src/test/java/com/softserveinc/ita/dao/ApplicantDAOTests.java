package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Applicant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ApplicantDAOTests extends BaseDAOTest {

    @Autowired
    private ApplicantDAO applicantDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testGetApplicants() {
        Session session = sessionFactory.getCurrentSession();
        session.save(new Applicant("TestApplicantOne", "Surname"));
        session.save(new Applicant("TestApplicantTwo", "Surname"));
        session.save(new Applicant("TestApplicantThree", "Surname"));
        List<Applicant> applicants = applicantDAO.getApplicants();
        int actualSize = applicants.size();
        assertEquals(3, actualSize);
    }

    @Test
    public void testGetApplicantById() {
        Session session = sessionFactory.getCurrentSession();
        Applicant expected = new Applicant("TestApplicant", "Surname");
        String applicantId = (String) session.save(expected);
        Applicant actual = applicantDAO.getApplicantById(applicantId);
        assertThat(expected, equalTo(actual));
    }

    @Test
    public void testAddNewApplicant() {
        Applicant expected = new Applicant("TestApplicant", "Surname");
        Applicant actual = applicantDAO.addNewApplicant(expected);
        assertThat(expected, equalTo(actual));
    }

    @Test
    public void testEditApplicant() {
        Applicant expected = new Applicant("ExpectedApplicant", "Surname");
        sessionFactory.getCurrentSession().save(expected);
        expected.setName("Actual");
        Applicant actual = applicantDAO.editApplicant(expected);
        assertThat(expected, equalTo(actual));
    }

    @Test
    public void testGetApplicantIdList() {
        Session session = sessionFactory.getCurrentSession();
        Applicant expectedOne = new Applicant("TestApplicantOne", "Surname");
        Applicant expectedTwo = new Applicant("TestApplicantTwo", "Surname");
        Applicant expectedThree = new Applicant("TestApplicantThree", "Surname");
        session.save(expectedOne);
        session.save(expectedTwo);
        session.save(expectedThree);
        List<String> applicantIdList = applicantDAO.getApplicantIdList();
        int actual = applicantIdList.size();
        assertEquals(3, actual);
    }
}
