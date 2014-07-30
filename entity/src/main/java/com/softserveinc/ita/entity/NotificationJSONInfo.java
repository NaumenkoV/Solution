package com.softserveinc.ita.entity;

import java.io.Serializable;

public class NotificationJSONInfo implements Serializable {

    private String applicantId;
    private String groupId;

    public NotificationJSONInfo() {
    }

    private String responsibleHrId;

    public NotificationJSONInfo(String applicantId, String groupId, String responsibleHrId){
        this.applicantId = applicantId;
        this.groupId = groupId;
        this.responsibleHrId = responsibleHrId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getResponsibleHrId() {
        return responsibleHrId;
    }

    public void setResponsibleHrId(String responsibleHrId) {
        this.responsibleHrId = responsibleHrId;
    }

    @Override
    public String toString() {
        return "NotificationJSONInfo{" +
                "applicantId='" + applicantId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", responsibleHrId='" + responsibleHrId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationJSONInfo info = (NotificationJSONInfo) o;

        if (applicantId != null ? !applicantId.equals(info.applicantId) : info.applicantId != null) return false;
        if (groupId != null ? !groupId.equals(info.groupId) : info.groupId != null) return false;
        if (responsibleHrId != null ? !responsibleHrId.equals(info.responsibleHrId) : info.responsibleHrId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applicantId != null ? applicantId.hashCode() : 0;
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (responsibleHrId != null ? responsibleHrId.hashCode() : 0);
        return result;
    }
}
