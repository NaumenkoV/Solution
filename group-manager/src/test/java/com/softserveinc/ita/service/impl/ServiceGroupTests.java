package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.dao.GroupDAO;
import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.exception.impl.GroupDoesntExistException;
import com.softserveinc.ita.exception.impl.GroupWithThisNameIsAlreadyExists;
import com.softserveinc.ita.service.GroupService;
import com.softserveinc.ita.service.ServiceGroupBaseTest;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceGroupTests extends ServiceGroupBaseTest {

    @Autowired
    @InjectMocks
    private GroupService groupService;

    @Autowired
    @Mock
    private GroupDAO groupDAO;

    private ArrayList<Group> groupList;
    private Group group1;
    private Group group2;
    private Group group3;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        groupList = new ArrayList<>();
        group1 = new Group("id1", new Course("Java", "pen-java.png"), "kv001");
        group1.setStartBoardingTime(100);
        group1.setStartTime(200);
        group1.setEndTime(300);
        groupList.add(group1);
        group2 = new Group("id4", new Course("Sharp", "pen-net.png"), "kv012");
        group2.setStartBoardingTime(new DateTime(200).getMillis());
        group2.setStartTime(300);
        group2.setEndTime(400);
        groupList.add(group2);
        group3 = new Group("id4", new Course("Sharp", "pen-net.png"), "kv012");
        group3.setStartBoardingTime(new DateTime(300).getMillis());
        group3.setStartTime(400);
        group3.setEndTime(500);
        groupList.add(group3);
    }


    @Test
    public void testGetGroupsByPlannedStatusAndExpectCorrectList() throws Exception {
        ArrayList<Group> expectedList = new ArrayList<Group>();
        expectedList.add(group2);
        expectedList.add(group3);
        when(groupDAO.getAllGroups()).thenReturn(groupList);
        List<Group> groupsByStatus = groupService.getGroupsByStatus(Group.Status.PLANNED, 150);
        assertEquals(groupsByStatus,expectedList);
    }

    @Test
    public void testGetGroupsByBoardingStatusAndExpectCorrectList() throws Exception {
        ArrayList<Group> expectedList = new ArrayList<Group>();
        when(groupDAO.getAllGroups()).thenReturn(groupList);
        List<Group> groupsByStatus = groupService.getGroupsByStatus(Group.Status.BOARDING, 200);
        assertEquals(groupsByStatus, expectedList);
    }

    @Test
    public void testGetGroupsByInProcessStatusAndExpectCorrectList() throws Exception {
        ArrayList<Group> expectedList = new ArrayList<Group>();
        expectedList.add(group2);
        when(groupDAO.getAllGroups()).thenReturn(groupList);
        List<Group> groupsByStatus = groupService.getGroupsByStatus(Group.Status.IN_PROCESS, 350);
        assertEquals(groupsByStatus, expectedList);
    }

    @Test
    public void testGetGroupsByFinishedStatusAndExpectCorrectList() throws Exception {
        ArrayList<Group> expectedList = new ArrayList<Group>();
        expectedList.add(group1);
        expectedList.add(group2);
        expectedList.add(group3);
        when(groupDAO.getAllGroups()).thenReturn(groupList);
        List<Group> groupsByStatus = groupService.getGroupsByStatus(Group.Status.FINISHED, 600);
        assertEquals(groupsByStatus, expectedList);
    }

    @Test
    public void testAddNewGroupAndExpectIsOk() throws GroupWithThisNameIsAlreadyExists {
        Group group = new Group();
        when(groupDAO.addGroup(group)).thenReturn(new Group("id100"));
        assertEquals("id100", groupService.createGroup(group).getGroupID());
    }

    @Test
    public void testGetgetAllGroupsAndExpectCorrectList() {
        ArrayList<Group> expectedList = new ArrayList<Group>();
        expectedList.add(new Group("id3", new Course("Java", "pen-java.png"), "kv021"));
        expectedList.add(new Group("id6", new Course("Java Script", "pen-net.png"), "kv061"));
        expectedList.add(new Group("id9", new Course("Java", "pen-java.png"), "kv041"));
        when(groupDAO.getAllGroups()).thenReturn(expectedList);
        assertEquals(expectedList, groupService.getAllGroups());
        verify(groupDAO, atLeastOnce()).getAllGroups();
    }

    @Test
    public void testGetGroupByExistingIdAndExpectCorrectGroup() throws Exception{
        Group group = new Group("id1");
        when(groupDAO.getGroupById("id1")).thenReturn(group);
        assertEquals(group,groupService.getGroupById("id1"));
    }

    @Test(expected = GroupDoesntExistException.class)
    public void testGetGroupByNotExistingIdAndExpectException()throws Exception{
        when(groupDAO.getGroupById("notExistingId")).thenThrow(GroupDoesntExistException.class);
        groupService.getGroupById("notExistingId");
    }

    @Test(expected = GroupDoesntExistException.class)
    public void testDeleteGroupByExistingIdAndExpectException() throws GroupDoesntExistException {
        doThrow(new RuntimeException()).when(groupDAO).removeGroup("id1");
        groupService.removeGroup("id1");
    }

    @Test
    public void testDeleteGroupByExistingIdAndExpectDaoCall() throws Exception {
        doNothing().when(groupDAO).removeGroup("id1");
        groupService.removeGroup("id1");
        verify(groupDAO, times(1)).removeGroup("id1");
    }

    @Test
    public void testUpdateGroupAndExpectTheSameGroup() throws Exception {
        Group group = new Group("id1");
        when(groupDAO.updateGroup(group)).thenReturn(group);
        assertEquals(groupService.updateGroup(group), group);
        verify(groupDAO, atLeastOnce()).updateGroup(group);
    }

    @Test(expected = GroupWithThisNameIsAlreadyExists.class)
    public void testUpdateGroupForExistingNameAndExpectException() throws Exception {
        Group group = new Group("id1");
        when(groupDAO.updateGroup(group)).thenThrow(Exception.class);
        groupService.updateGroup(group);
    }
}
