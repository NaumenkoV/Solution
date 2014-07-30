package com.softserveinc.ita.entity;

import com.softserveinc.ita.entity.exceptions.DateException;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Appointments")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final long DEFAULT_DURATION_TIME = 30 * 60 * 1000;
    private static final int TOMORROW = 24 * 60 * 60 * 1000;
    private static final long DEFAULT_ACTUAL_START_TIME = 0L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "Id", unique = true)
    private String appointmentId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Column(name = "UserIdList")
    private List <String> userIdList = new ArrayList<>();

    @Column(name = "ApplicantId")
    private String applicantId;

    @Column(name = "StartTime")
    private long startTime = System.currentTimeMillis() + TOMORROW;

    @Column(name = "DurationTime")
    private long durationTime = DEFAULT_DURATION_TIME;

    @Column(name = "ActualStartTime")
    private long actualStartTime = DEFAULT_ACTUAL_START_TIME;

    @Column(name = "GroupId")
    private String groupId;

    public Appointment() {}

    public Appointment(List<String> userIdList, String applicantId, long startTime, long durationTime) {
        this.userIdList = userIdList;
        this.applicantId = applicantId;
        this.startTime = startTime;
        this.durationTime = durationTime;
    }

    public Appointment(List<String> userIdList, String applicantId, long startTime) {
        this.userIdList = userIdList;
        this.applicantId = applicantId;
        this.startTime = startTime;
    }

    public Appointment(List<String> userIdList, String applicantId, long startTime, String groupId) {
        this.userIdList = userIdList;
        this.applicantId = applicantId;
        this.startTime = startTime;
        this.groupId = groupId;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public long getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(long actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;

        Appointment that = (Appointment) o;

        if (actualStartTime != that.actualStartTime) return false;
        if (durationTime != that.durationTime) return false;
        if (startTime != that.startTime) return false;
        if (applicantId != null ? !applicantId.equals(that.applicantId) : that.applicantId != null) return false;
        if (appointmentId != null ? !appointmentId.equals(that.appointmentId) : that.appointmentId != null)
            return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;
        if (userIdList != null ? !userIdList.equals(that.userIdList) : that.userIdList != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appointmentId != null ? appointmentId.hashCode() : 0;
        result = 31 * result + (userIdList != null ? userIdList.hashCode() : 0);
        result = 31 * result + (applicantId != null ? applicantId.hashCode() : 0);
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (durationTime ^ (durationTime >>> 32));
        result = 31 * result + (int) (actualStartTime ^ (actualStartTime >>> 32));
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", userIdList=" + userIdList +
                ", applicantId='" + applicantId + '\'' +
                ", startTime=" + startTime +
                ", durationTime=" + durationTime +
                ", actualStartTime=" + actualStartTime +
                ", groupId='" + groupId + '\'' +
                '}';
    }

    public void dateValidation(long startTime, long durationTime) throws DateException {

        if (durationTime < 0) throw new DateException("Wrong duration time");

        long currentDate=new Date().getTime();
        if (startTime < currentDate) throw new DateException("Start time has already passed");

        long bigDurationTime = 1000 * 60 * 60 * 12; //interview for the whole day, bad idea
        if (durationTime > bigDurationTime) throw new DateException("Too long duration time");
    }
}
