package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.dao.ApplicantDAO;
import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.exception.ApplicantDoesNotExistException;
import com.softserveinc.ita.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    @Autowired
    private ApplicantDAO applicantDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Applicant> getApplicants() {
        return applicantDao.getApplicants();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Applicant getApplicantById(String applicantId) throws ApplicantDoesNotExistException {
        Applicant searchedApplicant = applicantDao.getApplicantById(applicantId);
        if (searchedApplicant == null) {
            throw new ApplicantDoesNotExistException();
        }
        return searchedApplicant;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Applicant addNewApplicant(Applicant applicant) {
        return applicantDao.addNewApplicant(applicant);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Applicant editApplicant(Applicant applicant) {
        return applicantDao.editApplicant(applicant);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<String> getApplicantIDList() {
        List<Applicant> applicants = applicantDao.getApplicants();
        if (applicants == null || applicants.isEmpty()) {
            return null;
        }
        List<String> applicantIDs = new ArrayList<>();
        for (Applicant applicant : applicants) {
            applicantIDs.add(applicant.getId());
        }
        return applicantIDs;
    }
}
