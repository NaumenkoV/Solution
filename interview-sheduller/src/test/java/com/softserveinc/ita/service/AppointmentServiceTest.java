package com.softserveinc.ita.service;

import com.softserveinc.ita.BaseMVCTest;
import com.softserveinc.ita.entity.Appointment;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class AppointmentServiceTest extends BaseMVCTest {

    @Autowired
    AppointmentService service;
    private static Appointment expectedOne = null;
    private static Appointment expectedTwo = null;

    @BeforeClass
    public static void setUpOnce() {
        int TOMORROW = 24 * 60 * 60 * 1000;
        String appointmentId = "testAppointmentId";

        List<String> users = new ArrayList<>();
        users.add("testUserId");

        expectedOne = new Appointment(users, "testApplicantId", 1401866602L + TOMORROW);
        expectedOne.setAppointmentId(appointmentId);

        expectedTwo = new Appointment(users, "testApplicantId", 1401866604L + TOMORROW);
        expectedTwo.setAppointmentId(appointmentId);
    }

    @Test
    public void testGetAppointmentByApplicantIdAndExpectAppointmentEqualExpectedOne() {
        List<Appointment> actual = service.getAppointmentByApplicantId("testApplicantId");
        assertEquals(expectedOne, actual.get(0));
    }

    @Test
    public void testGetAppointmentByApplicantIdAndExpectAppointmentNonEqualExpectedOne() {
        List<Appointment> actual = service.getAppointmentByApplicantId("testApplicantId");
        assertNotSame(expectedOne, actual.get(0));
    }

    @Test
    public void testGetAppointmentByApplicantIdAndExpectAppointmentEqualExpectedTwo() {
        List<Appointment> actual = service.getAppointmentByApplicantId("testApplicantId2");
        assertEquals(expectedTwo, actual.get(0));
    }

    @Test
    public void testGetAppointmentByDateAndExpectAppointmentWithValidDate() {
        final int SAME_VALUE_BY_COMPARE = 0;

        List<Appointment> actualList = service.getAppointmentsByDate(System.currentTimeMillis());
        if (actualList.isEmpty()) {
            fail();
        }
        Appointment actual = actualList.get(0);
        DateTime toDay = DateTime.now();
        DateTime actualDate = new DateTime(actual.getStartTime());
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

        assertEquals(dateTimeComparator.compare(toDay, actualDate), SAME_VALUE_BY_COMPARE);
    }

    @Test
    public void testUpdateAppointment() {
        service.updateAppointment(expectedTwo);
    }

    @Test
    public void testGetAppointmentIdByGroupIdAndApplicantId() {
        String expectedId = "TestAppointmentId";
        String actualId = service.getAppointmentIdByGroupIdAndApplicantId("TestGroupId", "TestApplicantId");
        assertThat(expectedId, equalTo(actualId));
    }
}
