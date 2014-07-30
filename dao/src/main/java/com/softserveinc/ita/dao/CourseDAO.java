package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Course;

import java.util.List;

public interface CourseDAO {

    Course addCourse(Course course);

    List<Course> getAllCourses();

    void removeCourse(Course course);

    Course getCourseByName(String courseName);
}
