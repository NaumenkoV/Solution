package com.softserveinc.ita.entity;

import java.io.Serializable;

/**
 * Needed for JSON returning
 */
public class OperationStatusJSONInfo implements Serializable{
    private String status;

    public OperationStatusJSONInfo() {
    }

    public OperationStatusJSONInfo(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationStatusJSONInfo that = (OperationStatusJSONInfo) o;

        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return status != null ? status.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OperationStatusJSONInfo{" +
                "status='" + status + '\'' +
                '}';
    }
}
