package com.softserveinc.ita.dao.impl;

import com.softserveinc.ita.dao.QuestionInformationDAO;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class QuestionInformationDAOHibernate implements QuestionInformationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public QuestionInformation getQuestionInformationById(String questionsInformationId) {
        return (QuestionInformation) sessionFactory.getCurrentSession().get(QuestionInformation.class, questionsInformationId);
    }

    @Override
    public String updateQuestionInformation(QuestionInformation questionInformation) {
        sessionFactory.getCurrentSession().update(questionInformation);
        return questionInformation.getId();
    }

    @Override
    public void deleteQuestionInformationById(String questionInformationId) {
        sessionFactory.getCurrentSession().delete(getQuestionInformationById(questionInformationId));
    }
}
