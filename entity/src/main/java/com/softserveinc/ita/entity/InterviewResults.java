package com.softserveinc.ita.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Вадим
 * Date: 21.07.14
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class InterviewResults {

    private String interviewId;
    private String finalComment;
    private int totalPoints;

    public InterviewResults() {
    }

    public String getInterviewId() {
        return interviewId;
    }

    public String getFinalComment() {
        return finalComment;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterviewResults)) return false;

        InterviewResults that = (InterviewResults) o;

        if (totalPoints != that.totalPoints) return false;
        if (finalComment != null ? !finalComment.equals(that.finalComment) : that.finalComment != null) return false;
        if (interviewId != null ? !interviewId.equals(that.interviewId) : that.interviewId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = interviewId != null ? interviewId.hashCode() : 0;
        result = 31 * result + (finalComment != null ? finalComment.hashCode() : 0);
        result = 31 * result + totalPoints;
        return result;
    }

    @Override
    public String toString() {
        return "InterviewResults{" +
                "interviewId='" + interviewId + '\'' +
                ", finalComment='" + finalComment + '\'' +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
