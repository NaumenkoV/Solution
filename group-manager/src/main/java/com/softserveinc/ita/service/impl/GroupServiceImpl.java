package com.softserveinc.ita.service.impl;


import com.softserveinc.ita.dao.CourseDAO;
import com.softserveinc.ita.dao.GroupDAO;
import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.entity.ApplicantBenchmark;
import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.exception.impl.GroupDoesntExistException;
import com.softserveinc.ita.exception.impl.GroupWithThisNameIsAlreadyExists;
import com.softserveinc.ita.service.GroupService;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<Group> getGroupsByStatus(Group.Status groupStatus, long currentTime) {
        List<Group> groups = groupDAO.getAllGroups();
        ArrayList<Group> choosenGroups = new ArrayList<>();
        for (Group group : groups) {
            switch (groupStatus) {
                case PLANNED:
                    if (currentTime < group.getStartBoardingTime()) {
                        choosenGroups.add(group);
                    }
                    break;
                case BOARDING:
                    if (currentTime > group.getStartBoardingTime() && currentTime < group.getStartTime()) {
                        choosenGroups.add(group);
                    }
                    break;
                case IN_PROCESS:
                    if (currentTime > group.getStartTime() && currentTime < group.getEndTime()) {
                        choosenGroups.add(group);
                    }
                    break;
                case FINISHED:
                    if (currentTime > group.getEndTime()) {
                        choosenGroups.add(group);
                    }
                    break;
            }
        }
        return choosenGroups;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<Course> getCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        return courses;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Group createGroup(Group group) throws GroupWithThisNameIsAlreadyExists {
        try {
            Course course = courseDAO.getCourseByName(group.getCourse().getName());
            group.setCourse(course);
            return groupDAO.addGroup(group);
        } catch (JDBCException e) {
            throw new GroupWithThisNameIsAlreadyExists();
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<Group> getAllGroups() {
        return groupDAO.getAllGroups();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Group getGroupById(String id) throws GroupDoesntExistException {
        Group searchedGroup = groupDAO.getGroupById(id);
        if(searchedGroup == null){
            throw new GroupDoesntExistException();
        }
        return searchedGroup;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void removeGroup(String groupId) throws GroupDoesntExistException {
        try {
            groupDAO.removeGroup(groupId);
        } catch (Exception e) {
            throw new GroupDoesntExistException();
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Group updateGroup(Group group) throws GroupWithThisNameIsAlreadyExists, GroupDoesntExistException {
        try {
            Course course = courseDAO.getCourseByName(group.getCourse().getName());
            group.setCourse(course);
            Group updatedGroup = groupDAO.updateGroup(group);
            return updatedGroup;
        }
        catch(JDBCException e){
            throw new GroupWithThisNameIsAlreadyExists();
        }
        catch (Exception e) {
            throw new GroupDoesntExistException();
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int getGroupCapacity(String groupID) {
        return groupDAO.getGroupById(groupID).getCapacity();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Group updateApplicantsInGroup(String groupID,
                                         Map<String, ApplicantBenchmark> applicants) {
        Group group = groupDAO.getGroupById(groupID);
        Map<String, ApplicantBenchmark> groupApplicants = group.getApplicants();
        for (Map.Entry<String, ApplicantBenchmark> entry : applicants.entrySet()) {
            groupApplicants.put(entry.getKey(), entry.getValue());
        }
        return groupDAO.updateGroup(group);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Map<String, ApplicantBenchmark> getApplicantsByGroupIdAndStatus(
            String groupID, Applicant.Status status) {
        Group group = groupDAO.getGroupById(groupID);
        Map<String, ApplicantBenchmark> groupApplicants = group.getApplicants();
        Map<String, ApplicantBenchmark> result = new HashMap<>();
        for (Map.Entry<String, ApplicantBenchmark> entry : groupApplicants.entrySet()) {
            if (entry.getValue().getStatus().equals(status)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

}
