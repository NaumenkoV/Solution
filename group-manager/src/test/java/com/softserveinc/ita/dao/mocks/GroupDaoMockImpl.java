package com.softserveinc.ita.dao.mocks;

import com.softserveinc.ita.dao.GroupDAO;
import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class GroupDaoMockImpl implements GroupDAO {
    private ArrayList<Group> groups = new ArrayList<Group>();

    public GroupDaoMockImpl() {
        //Planned
        Group group1 = new Group("id1", new Course("Java", "pen-java.png"), "kv001");
        group1 = setTime(group1, new DateTime(2014, 8, 1, 0, 0, 0).getMillis(),
                new DateTime(2014, 8, 10, 0, 0, 0).getMillis(), new DateTime(2014, 8, 20, 0, 0, 0).getMillis());
        groups.add(group1);
        Group group2 = new Group("id3", new Course("Java", "pen-java.png"), "kv021");
        group2 = setTime(group2, new DateTime(2014, 8, 1, 0, 0, 0).getMillis(),
                new DateTime(2014, 8, 10, 0, 0, 0).getMillis(), new DateTime(2014, 8, 20, 0, 0, 0).getMillis());
        groups.add(group2);
        //Boarding
        Group group3 = new Group("id4", new Course("Sharp", "pen-net.png"), "kv012");
        group3 = setTime(group3, new DateTime(2014, 7, 17, 0, 0, 0).getMillis(),
                new DateTime(2014, 8, 10, 0, 0, 0).getMillis(), new DateTime(2014, 8, 20, 0, 0, 0).getMillis());
        groups.add(group3);
        Group group4 = new Group("id6", new Course("JavaScript", "pen-jsui.png"), "kv061");
        group4 = setTime(group4, new DateTime(2014, 7, 17, 0, 0, 0).getMillis(),
                new DateTime(2014, 8, 10, 0, 0, 0).getMillis(), new DateTime(2014, 8, 20, 0, 0, 0).getMillis());
        groups.add(group4);
        //InProcess
        Group group5 = new Group("id9", new Course("Java", "pen-java.png"), "kv041");
        group5 = setTime(group5, new DateTime(2014, 7, 10, 0, 0, 0).getMillis(),
                new DateTime(2014, 7, 15, 0, 0, 0).getMillis(), new DateTime(2014, 7, 30, 0, 0, 0).getMillis());
        groups.add(group5);
        Group group6 = new Group("id10", new Course("Sharp", "pen-net.png"), "kv064");
        group6 = setTime(group6, new DateTime(2014, 7, 10, 0, 0, 0).getMillis(),
                new DateTime(2014, 7, 15, 0, 0, 0).getMillis(), new DateTime(2014, 7, 30, 0, 0, 0).getMillis());
        groups.add(group6);
        //Finished
        Group group7 = new Group("id12", new Course("JavaScript", "pen-jsui.png"), "kv532");
        group7 = setTime(group7, new DateTime(2014, 7, 10, 0, 0, 0).getMillis(),
                new DateTime(2014, 7, 15, 0, 0, 0).getMillis(), new DateTime(2014, 7, 20, 0, 0, 0).getMillis());
        groups.add(group7);
        Group group8 = new Group("id13", new Course("DevOps", "pen-devops.png"), "kv0753");
        group8 = setTime(group8, new DateTime(2014, 7, 10, 0, 0, 0).getMillis(),
                new DateTime(2014, 7, 15, 0, 0, 0).getMillis(), new DateTime(2014, 7, 20, 0, 0, 0).getMillis());
        groups.add(group8);
        Group group9 = new Group("id15", new Course("JavaScript", "pen-jsui.png"), "kv532");
        group9 = setTime(group9, new DateTime(2014, 7, 10, 0, 0, 0).getMillis(),
                new DateTime(2014, 7, 15, 0, 0, 0).getMillis(), new DateTime(2014, 7, 20, 0, 0, 0).getMillis());
        groups.add(group9);
    }

    public static Group setTime(Group group, long boardingTime, long startTime, long endTime) {
        group.setStartBoardingTime(boardingTime);
        group.setStartTime(startTime);
        group.setEndTime(endTime);
        return group;
    }

    @Override
    public Group addGroup(Group group) {
        groups.add(group);
        group.setGroupID("id100");
        setCourseRef(group);
        return group;
    }

    private void setCourseRef(Group group) {
        switch (group.getCourse().getName()) {
            case "DevOps":
                group.getCourse().setImageRef("pen-devops.png");
                break;
            case "JavaScript":
                group.getCourse().setImageRef("pen-jsui.png");
                break;
            case "Java":
                group.getCourse().setImageRef("pen-java.png");
                break;
            case "Sharp":
                group.getCourse().setImageRef("pen-net.png");
                break;
        }
    }

    @Override
    public List<Group> getAllGroups() {
        return groups;
    }

    @Override
    public Group getGroupById(String id) {
        for (Group group : groups) {
            if (group.getGroupID().equals(id)) {
                return group;
            }
        }
        return null;
    }

    @Override
    public void removeGroup(String groupId) {
        for (int i = 0; i < groups.size(); i++) {
            if (groupId.equals(groups.get(i).getGroupID())) {
                groups.remove(i);
                return;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public Group updateGroup(Group group) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupID().equals(group.getGroupID())) {
                setCourseRef(group);
                groups.set(i, group);
                return group;
            }
        }
        throw new RuntimeException();
    }
}