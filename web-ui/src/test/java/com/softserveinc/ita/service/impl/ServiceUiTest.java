package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.entity.Course;
import com.softserveinc.ita.entity.Group;
import com.softserveinc.ita.service.BaseServiceUiTests;
import com.softserveinc.ita.service.HttpRequestExecutor;
import com.softserveinc.ita.service.ViewResolverService;
import com.softserveinc.ita.service.exception.HttpRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ServiceUiTest extends BaseServiceUiTests {

    @Autowired
    @InjectMocks
    private ViewResolverService viewResolverService;

    @Mock
    private HttpRequestExecutor httpRequestExecutor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetViewByPlannedGroupIdAndExpectPlannedString() throws HttpRequestException {
        Group group1 = new Group("id1", new Course("Java", "pen-java.png"), "kv001");
        group1.setStartBoardingTime(100);
        group1.setStartTime(200);
        group1.setEndTime(300);
        when(httpRequestExecutor.getObjectByID(group1.getGroupID(), Group.class)).thenReturn(group1);
        String pageName = viewResolverService.choosePageByGroupId(group1.getGroupID(), 50);
        assertEquals("planned", pageName);
    }

    @Test
    public void testGetViewByPlannedGroupIdAndExpectBoardingString() throws HttpRequestException {
        Group group1 = new Group("id1", new Course("Java", "pen-java.png"), "kv001");
        group1.setStartBoardingTime(100);
        group1.setStartTime(200);
        group1.setEndTime(300);
        when(httpRequestExecutor.getObjectByID(group1.getGroupID(), Group.class)).thenReturn(group1);
        String pageName = viewResolverService.choosePageByGroupId(group1.getGroupID(), 150);
        assertEquals("boarding", pageName);
    }

    @Test
    public void testGetViewByPlannedGroupIdAndExpectInProgressString() throws HttpRequestException {
        Group group1 = new Group("id1", new Course("Java", "pen-java.png"), "kv001");
        group1.setStartBoardingTime(100);
        group1.setStartTime(200);
        group1.setEndTime(300);
        when(httpRequestExecutor.getObjectByID(group1.getGroupID(), Group.class)).thenReturn(group1);
        String pageName = viewResolverService.choosePageByGroupId(group1.getGroupID(), 250);
        assertEquals("inprogress", pageName);
    }

    @Test
    public void testGetViewByPlannedGroupIdAndExpect404String() throws HttpRequestException {
        Group group1 = new Group("id1", new Course("Java", "pen-java.png"), "kv001");
        group1.setStartBoardingTime(100);
        group1.setStartTime(200);
        group1.setEndTime(300);
        when(httpRequestExecutor.getObjectByID(group1.getGroupID(), Group.class)).thenReturn(group1);
        String pageName = viewResolverService.choosePageByGroupId(group1.getGroupID(), 350);
        assertEquals("404", pageName);
    }
}
