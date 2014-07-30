package com.softserveinc.ita.dao.impl;

import com.softserveinc.ita.dao.CourseDAO;
import com.softserveinc.ita.entity.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class CourseDAOHibernate implements CourseDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Course addCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        Serializable courseId = session.save(course);
        return (Course) session.get(Course.class, courseId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) sessionFactory.getCurrentSession()
                .createCriteria(Course.class).list();
    }

    @Override
    public void removeCourse(Course course) {
        sessionFactory.getCurrentSession().delete(course);
    }

    @Override
    public Course getCourseByName(String courseName) {
        return (Course) sessionFactory.getCurrentSession().createCriteria(Course.class)
                .add(Restrictions.like("name", courseName)).uniqueResult();
    }
}
