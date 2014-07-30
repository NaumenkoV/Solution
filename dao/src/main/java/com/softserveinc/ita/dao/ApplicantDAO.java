package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Applicant;

import java.util.List;

public interface ApplicantDAO {

    List<Applicant> getApplicants();

    Applicant getApplicantById(String applicantId);

    Applicant addNewApplicant(Applicant applicant);

    Applicant editApplicant(Applicant applicant);

    List<String> getApplicantIdList();
}
