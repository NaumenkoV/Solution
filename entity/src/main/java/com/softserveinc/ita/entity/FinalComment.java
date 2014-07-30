package com.softserveinc.ita.entity;

public class FinalComment {

    private String finalComment;
    private int bonusPoints;
    private String interviewId;

    public FinalComment() {
    }

    public FinalComment(String finalComment, int bonusPoints, String interviewId) {
        this.finalComment = finalComment;
        this.bonusPoints = bonusPoints;
        this.interviewId = interviewId;
    }

    public String getFinalComment() {
        return finalComment;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinalComment)) return false;

        FinalComment that = (FinalComment) o;

        if (bonusPoints != that.bonusPoints) return false;
        if (finalComment != null ? !finalComment.equals(that.finalComment) : that.finalComment != null) return false;
        if (interviewId != null ? !interviewId.equals(that.interviewId) : that.interviewId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = finalComment != null ? finalComment.hashCode() : 0;
        result = 31 * result + bonusPoints;
        result = 31 * result + (interviewId != null ? interviewId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FinalComment{" +
                "finalComment='" + finalComment + '\'' +
                ", bonusPoints=" + bonusPoints +
                ", interviewId='" + interviewId + '\'' +
                '}';
    }
}
