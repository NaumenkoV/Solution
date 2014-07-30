package com.softserveinc.ita.entity;

import java.io.Serializable;

public class ApplicantBenchmark implements Serializable{
    private Applicant.Status status = Applicant.Status.NOT_SCHEDULED;
    private int rank = -1;

    public Applicant.Status getStatus() {
        return status;
    }

    public void setStatus(Applicant.Status status) {
        this.status = status;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }



    @Override
    public String toString() {
        return "ApplicantBenchmark{" +
                "status=" + status +
                ", rank=" + rank +
                '}';
    }

    public ApplicantBenchmark(Applicant.Status status, int rank) {
        this.status = status;
        this.rank = rank;
    }

    public ApplicantBenchmark() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicantBenchmark that = (ApplicantBenchmark) o;

        if (rank != that.rank) return false;
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + rank;
        return result;
    }
}