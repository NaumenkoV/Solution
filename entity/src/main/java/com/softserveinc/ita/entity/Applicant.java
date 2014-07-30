package com.softserveinc.ita.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Applicants")
public class Applicant implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String NOT_SCHEDULED_TEMPLATE_REF = "mailTemplaits/notScheduledLetter.vm";
    private static final String SCHEDULED_TEMPLATE_REF = "mailTemplaits/scheduledLetter.vm";
    private static final String PASSED_TEMPLATE_REF = "mailTemplaits/passedLetter.vm";
    private static final String NOT_PASSED_TEMPLATE_REF = "mailTemplaits/notPassedLetter.vm";
    private static final String EMPLOYED_TEMPLATE_REF = "mailTemplaits/employedLetter.vm";
    private static final String NOT_SCHEDULED_SUBJECT = "Application accepted";
    private static final String SCHEDULED_SUBJECT = "Interview invitation";
    private static final String PASSED_SUBJECT = "Interview passed";
    private static final String NOT_PASSED_SUBJECT = "Interview failed";
    private static final String EMPLOYED_SUBJECT = "Employed";


    public enum Status {
        NOT_SCHEDULED(NOT_SCHEDULED_TEMPLATE_REF, NOT_SCHEDULED_SUBJECT),
        SCHEDULED(SCHEDULED_TEMPLATE_REF, SCHEDULED_SUBJECT),
        PASSED(PASSED_TEMPLATE_REF, PASSED_SUBJECT),
        NOT_PASSED(NOT_PASSED_TEMPLATE_REF, NOT_PASSED_SUBJECT),
        EMPLOYED(EMPLOYED_TEMPLATE_REF, EMPLOYED_SUBJECT);

        private String templateRef;
        private String subject;

        Status(String templateRef, String subject) {
            this.templateRef = templateRef;
            this.subject = subject;
        }

        public String getTemplateRef() {
            return templateRef;
        }

        public void setTemplateRef(String templateRef) {
            this.templateRef = templateRef;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

    ;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "Id", unique = true)
    private String id;

    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @Column(name = "Email", unique = true)
    private String email;

    @Override
    public String toString() {
        return "Applicant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Applicant applicant = (Applicant) o;

        if (birthday != applicant.birthday) return false;
        if (email != null ? !email.equals(applicant.email) : applicant.email != null) return false;
        if (id != null ? !id.equals(applicant.id) : applicant.id != null) return false;
        if (name != null ? !name.equals(applicant.name) : applicant.name != null) return false;
        if (phone != null ? !phone.equals(applicant.phone) : applicant.phone != null) return false;
        if (surname != null ? !surname.equals(applicant.surname) : applicant.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (int) (birthday ^ (birthday >>> 32));
        return result;
    }

    @Column(name = "Phone")
    private String phone;
    @Column(name = "Birthday")
    private long birthday;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public Applicant() {
    }

    public Applicant(String applicantId) {
        this.id = applicantId;
    }

    public Applicant(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Applicant(String id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
