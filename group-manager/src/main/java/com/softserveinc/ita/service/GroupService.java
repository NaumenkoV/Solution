package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.Applicant;
import com.softserveinc.ita.entity.ApplicantBenchmark;
import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.exception.impl.GroupDoesntExistException;
import com.softserveinc.ita.exception.impl.GroupWithThisNameIsAlreadyExists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GroupService {
    List<Group> getGroupsByStatus(Group.Status groupStatus, long currentTime);

    List<Course> getCourses();

    Group createGroup(Group group) throws GroupWithThisNameIsAlreadyExists;

    List<Group> getAllGroups();

    Group getGroupById(String id) throws GroupDoesntExistException;

    void removeGroup(String groupId) throws GroupDoesntExistException;

    Group updateGroup(Group group) throws GroupWithThisNameIsAlreadyExists, GroupDoesntExistException;
    
    int getGroupCapacity(String groupID);

    Group updateApplicantsInGroup(String groupID, Map<String,
            ApplicantBenchmark> applicants);

    Map<String, ApplicantBenchmark> getApplicantsByGroupIdAndStatus(
            String groupID, Applicant.Status status);
}
