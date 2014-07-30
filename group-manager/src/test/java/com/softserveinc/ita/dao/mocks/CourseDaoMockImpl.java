package com.softserveinc.ita.dao.mocks;

import com.softserveinc.ita.dao.CourseDAO;
import com.softserveinc.ita.entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDaoMockImpl implements CourseDAO {

    List<Course> courses = new ArrayList<>();

    public CourseDaoMockImpl() {
        courses = new ArrayList<>();
        courses.add(new Course("DevOps", "pen-devops.png"));
        courses.add(new Course("JavaScript", "pen-jsui.png"));
        courses.add(new Course("Java", "pen-java.png"));
        courses.add(new Course("Sharp", "pen-net.png"));
    }

    @Override
    public Course addCourse(Course course) {
        courses.add(course);
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return courses;
    }

    @Override
    public void removeCourse(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            courses.remove(course);
        }
    }

    @Override
    public Course getCourseByName(String courseName) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }
}
