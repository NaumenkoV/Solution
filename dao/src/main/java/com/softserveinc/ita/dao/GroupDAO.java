package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Group;

import java.util.List;

public interface GroupDAO {

    Group addGroup(Group group);

    List<Group> getAllGroups();

    Group getGroupById(String groupId);

    void removeGroup(String groupId);

    Group updateGroup(Group group);
}
