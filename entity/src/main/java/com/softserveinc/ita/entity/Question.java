package com.softserveinc.ita.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Questions")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WEIGHT = 1;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "question_id", unique = true)
    private String questionId;

    @Column(name = "Question")
    private String QuestionBody;

    @Column(name = "Weight")
    private int weight = DEFAULT_WEIGHT;

    public Question() {
    }

    public Question(String questionBody, int weight) {
        QuestionBody = questionBody;
        this.weight = weight;
    }

    public String getQuestionBody() {
        return QuestionBody;
    }

    public int getWeight() {
        return weight;
    }

    public void setQuestionBody(String questionBody) {
        QuestionBody = questionBody;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (weight != question.weight) return false;
        if (QuestionBody != null ? !QuestionBody.equals(question.QuestionBody) : question.QuestionBody != null)
            return false;
        if (questionId != null ? !questionId.equals(question.questionId) : question.questionId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (QuestionBody != null ? QuestionBody.hashCode() : 0);
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId='" + questionId + '\'' +
                ", QuestionBody='" + QuestionBody + '\'' +
                ", weight=" + weight +
                '}';
    }
}
