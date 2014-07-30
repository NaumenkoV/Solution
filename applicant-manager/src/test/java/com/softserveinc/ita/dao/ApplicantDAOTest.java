package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Applicant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.*;

public class ApplicantDAOTest extends BaseApplicantDAOTest {

    @Autowired
    private ApplicantDAO applicantDao;

    @Test
    public void testGetApplicantByExistingIdAndExpectEquals() throws Exception {
        String applicantId = "id1";
        Applicant expectedApplicant = new Applicant("id1");
        assertEquals(expectedApplicant, applicantDao.getApplicantById(applicantId));
    }

    @Test
    public void testGetApplicantByNotExistingIdAndExpectNull() throws Exception {
        String applicantId = "id4";
        assertEquals(null, applicantDao.getApplicantById(applicantId));
    }

    @Test
    public void testAddApplicantAndGetTheSameApplicant() throws Exception {
        Applicant newApplicant = applicantDao.addNewApplicant(new Applicant());
        assertNotNull(newApplicant);
        assertFalse(newApplicant.getId().isEmpty());
        Applicant receivedApplicant = applicantDao.getApplicantById(newApplicant.getId());
        assertEquals(newApplicant, receivedApplicant);
    }

    @Test
    public void testAddApplicantAndEditTheSameApplicant() throws Exception {
        Applicant newApplicant = applicantDao.addNewApplicant(new Applicant());
        assertNotNull(newApplicant);
        assertFalse(newApplicant.getId().isEmpty());
        Applicant editedApplicant = applicantDao.editApplicant(newApplicant);
        assertEquals(newApplicant.getId(), editedApplicant.getId());
    }

    @Test
    public void testGetApplicantListAndExpectDefinedValues() throws Exception {
        List<Applicant> expectedApplicantList = new ArrayList<>();
        Collections.addAll(expectedApplicantList, new Applicant("123"), new Applicant("124"), new Applicant("125"));
        assertEquals(expectedApplicantList, applicantDao.getApplicants());
    }
}
