package com.softserveinc.ita.entity;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "QuestionInformation")
public class QuestionInformation implements Serializable {

    public static final String DEFAULT_COMMENT = "";
    public static final int DEFAULT_MARK = 1;
    public static final int DEFAULT_WEIGHT = 1;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "questionInformation_id", unique = true)
    private String Id;

    @Column(name = "Interview_id")
    private String interviewId = "";

    @Column(name = "Question")
    private String question = "";

    @Column(name = "Answer")
    private String answer = "";

    @Column(name = "Mark")
    private int mark = DEFAULT_MARK;

    @Column(name = "Comment")
    private String comment = DEFAULT_COMMENT;

    @Column(name = "Weight")
    private int weight = DEFAULT_WEIGHT; //weight for each question

    public QuestionInformation() {
    }

    public QuestionInformation(String question, int weight) {
        this.question = question;
        this.weight = weight;
    }

    public QuestionInformation(String question, String answer, int mark, String comment, int weight) {
        this.question = question;
        this.answer = answer;
        this.mark = mark;
        this.comment = comment;
        this.weight = weight;
    }

    public String getId() {
        return Id;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMark() {
        return mark;
    }

    public String getComment() {
        return comment;
    }

    public int getWeight() {
        return weight;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionInformation)) return false;

        QuestionInformation that = (QuestionInformation) o;

        if (mark != that.mark) return false;
        if (weight != that.weight) return false;
        if (Id != null ? !Id.equals(that.Id) : that.Id != null) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (interviewId != null ? !interviewId.equals(that.interviewId) : that.interviewId != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (interviewId != null ? interviewId.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return "QuestionInformation{" +
                "Id='" + Id + '\'' +
                ", interviewId='" + interviewId + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", mark=" + mark +
                ", comment='" + comment + '\'' +
                ", weight=" + weight +
                '}';
    }
}
