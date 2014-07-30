package com.softserveinc.ita.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "Id", unique = true)
    private String courseId="";
    @Column(name = "Name", unique = true)
    private String name = "";
    @Column(name = "ImageReference", unique = true)
    private String imageRef = "";

    public Course(){

    }

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, String imageRef) {
        this.name = name;
        this.imageRef = imageRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", courseId='" + courseId + '\'' +
                ", imageRef='" + imageRef + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;

        if (!courseId.equals(course.courseId)) return false;
        if (!imageRef.equals(course.imageRef)) return false;
        if (!name.equals(course.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + courseId.hashCode();
        result = 31 * result + imageRef.hashCode();
        return result;
    }
}
