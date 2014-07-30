package com.softserveinc.ita.dao.impl;

import com.softserveinc.ita.dao.InterviewDAO;
import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class InterviewDAOHibernate implements InterviewDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Interview getInterviewByAppointmentId(String appointmentId) {
        return (Interview) sessionFactory.getCurrentSession().get(Interview.class, appointmentId);
    }

    @Override
    public String putInterview(Interview interview) {
        return (String) sessionFactory.getCurrentSession().save(interview);
    }

    @Override
    public void removeInterviewByAppointmentId(String appointmentId) {
        Interview interview = (Interview) sessionFactory.getCurrentSession().load(Interview.class, appointmentId);
        if (interview != null) {
            sessionFactory.getCurrentSession().delete(interview);
        }
    }

    @Override
    public void updateInterview(Interview interview) {
        sessionFactory.getCurrentSession().update(interview);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Interview> getAllInterviews() {
        return (List<Interview>) sessionFactory.getCurrentSession().createCriteria(Interview.class).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getAllInterviewsId() {
        return (List<String>) sessionFactory.getCurrentSession().createCriteria(Interview.class)
                .setProjection(Projections.projectionList().add(Projections.property("interviewId"))).list();
    }

}
