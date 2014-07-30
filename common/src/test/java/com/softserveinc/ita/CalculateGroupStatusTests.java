package com.softserveinc.ita;

import com.softserveinc.ita.entity.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class CalculateGroupStatusTests {

    @Test
    public void testCalculateGroupStatusAndExpectPlannedStatus() {
        assertEquals(GroupStatusCalculator.calculateGroupStatus(100, 200, 300, 50), Group.Status.PLANNED);
    }

    @Test
    public void testCalculateGroupStatusAndExpectBoardingStatus() {
        assertEquals(GroupStatusCalculator.calculateGroupStatus(100, 200, 300, 150), Group.Status.BOARDING);
    }

    @Test
    public void testCalculateGroupStatusAndExpectInProcessStatus() {
        assertEquals(GroupStatusCalculator.calculateGroupStatus(100, 200, 300, 250), Group.Status.IN_PROCESS);
    }

    @Test
    public void testCalculateGroupStatusAndExpectFinishedStatus() {
        assertEquals(GroupStatusCalculator.calculateGroupStatus(100, 200, 300, 350), Group.Status.FINISHED);
    }

}
