package com.segmnf.myapplication.Model;

public class ContestModel {

    String name;
    String date;
    String duration;
    String questions;
    String marks;
    String id;
    String start;
    String end;
    String qid;
    String adminid;
    String score;
    String millis;
    String submissiontime;
    String tough;
    String Editorial;

    public ContestModel() {
    }

    public ContestModel(String name, String date, String duration, String questions, String marks, String id, String start, String end, String qid, String adminid, String score, String millis, String submissiontime, String tough, String editorial) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.questions = questions;
        this.marks = marks;
        this.id = id;
        this.start = start;
        this.end = end;
        this.qid = qid;
        this.adminid = adminid;
        this.score = score;
        this.millis = millis;
        this.submissiontime = submissiontime;
        this.tough = tough;
        Editorial = editorial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getMillis() {
        return millis;
    }

    public void setMillis(String millis) {
        this.millis = millis;
    }

    public String getSubmissiontime() {
        return submissiontime;
    }

    public void setSubmissiontime(String submissiontime) {
        this.submissiontime = submissiontime;
    }

    public String getTough() {
        return tough;
    }

    public void setTough(String tough) {
        this.tough = tough;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String editorial) {
        Editorial = editorial;
    }
}