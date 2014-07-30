package com.softserveinc.ita.dao;


import com.softserveinc.ita.entity.Course;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CourseDAOTests extends BaseDAOTest {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testAddCourse() {
        Course expected = new Course("TestCourseName", "TestRef");
        Course course = courseDAO.addCourse(expected);
        Course actual =(Course) sessionFactory.getCurrentSession().get(Course.class, course.getCourseId());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllCourses() {
        Course courseOne = new Course("TestCourseOne", "TestRefOne");
        Course courseTwo = new Course("TestCourseTwo", "TestRefTwo");
        Course courseThree = new Course("TestCourseThree", "TestRefThree");
        Session session = sessionFactory.getCurrentSession();
        session.save(courseOne);
        session.save(courseTwo);
        session.save(courseThree);
        List<Course> allCourses = courseDAO.getAllCourses();
        assertEquals(3, allCourses.size());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testRemoveCourse() {
        Course course = new Course("TestCourseOne", "TestRefOne");
        Session session = sessionFactory.getCurrentSession();
        Serializable courseId = session.save(course);
        Course courseDelete = (Course)session.get(Course.class, courseId);
        courseDAO.removeCourse(courseDelete);
        session.load(Course.class, courseId);
    }

    @Test
    public void testGetCourseByName() {
        Session session = sessionFactory.getCurrentSession();
        String courseName = "ExpectedName";
        Course expected = new Course(courseName, "refOne");
        Course course = new Course("SimpleName", "refTwo");
        session.save(expected);
        session.save(course);
        Course actual = courseDAO.getCourseByName(courseName);
        assertEquals(expected, actual);
    }
}
