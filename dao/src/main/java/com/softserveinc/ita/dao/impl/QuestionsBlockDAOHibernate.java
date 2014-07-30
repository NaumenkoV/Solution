package com.softserveinc.ita.dao.impl;

import com.softserveinc.ita.dao.QuestionsBlockDAO;
import com.softserveinc.ita.entity.Appointment;
import com.softserveinc.ita.entity.Interview;
import com.softserveinc.ita.entity.QuestionInformation;
import com.softserveinc.ita.entity.QuestionsBlock;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class QuestionsBlockDAOHibernate implements QuestionsBlockDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public QuestionsBlock getQuestionsBlockFromInterviewByQuestionsBlockId(String questionsBlockId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionsBlock.class)
                .add(Restrictions.like("Id", questionsBlockId));

        return (QuestionsBlock) criteria.uniqueResult();
    }

    @Override
    public String updateQuestionsBlock(QuestionsBlock questionsBlock) {
        sessionFactory.getCurrentSession().update(questionsBlock);
        return questionsBlock.getId();
    }

    @Override
    public QuestionsBlock getQuestionsBlockByInterviewIdAndUserId(String userID, String appointmentId) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(QuestionsBlock.class)
                .add(Restrictions.like("userId", userID))
                .add(Restrictions.like("interviewId", appointmentId));

        return (QuestionsBlock) criteria.uniqueResult();

    }

    @Override
    public void deleteQuestionsBlockById(String questionsBlockId) {
        sessionFactory.getCurrentSession().delete(getQuestionsBlockFromInterviewByQuestionsBlockId(questionsBlockId));
    }

    @Override
    public QuestionsBlock getStandardQuestionsBlockFromInterview(String interviewId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QuestionsBlock getStandardQuestionsBlock() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setStandardQuestionsBlock(QuestionsBlock standardQuestionsBlock) {
    }

}
