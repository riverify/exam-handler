package com.riverify.school.domain;

import java.util.Arrays;
import java.util.Date;

/**
 * ManageForm -
 *
 * @author riverify
 * @version JDK 8
 */
public class ManageForm {
    private Date date;

    private Integer duration;

    private Integer relax;

    private String[] startend;

    private String subject;

    private String teacher;

    public ManageForm() {
    }

    public ManageForm(Date date, Integer duration, Integer relax, String[] startend, String subject, String teacher) {
        this.date = date;
        this.duration = duration;
        this.relax = relax;
        this.startend = startend;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRelax() {
        return relax;
    }

    public void setRelax(Integer relax) {
        this.relax = relax;
    }

    public String[] getStartend() {
        return startend;
    }

    public void setStartend(String[] startend) {
        this.startend = startend;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "ManageForm{" +
                "date=" + date +
                ", duration=" + duration +
                ", relax=" + relax +
                ", startend=" + Arrays.toString(startend) +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
