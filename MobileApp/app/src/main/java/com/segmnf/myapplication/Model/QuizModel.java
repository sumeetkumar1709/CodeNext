package com.segmnf.myapplication.Model;

public class QuizModel {
    String adminid;
    String quizid;
    String name;
    String topic;
    String visibility;
    String rules;
    String questioncount;
    String duration;
    String userscore;
    String isgiven;
    String difficulty;
    String quid;
    String start;
    String end;
    String date;
    String submittime;
    String isresultvisible;

    public QuizModel() {
    }

    public QuizModel(String adminid, String quizid, String name, String topic, String visibility, String rules, String questioncount, String duration, String userscore, String isgiven, String difficulty, String quid, String start, String end, String date, String submittime, String isresultvisible) {
        this.adminid = adminid;
        this.quizid = quizid;
        this.name = name;
        this.topic = topic;
        this.visibility = visibility;
        this.rules = rules;
        this.questioncount = questioncount;
        this.duration = duration;
        this.userscore = userscore;
        this.isgiven = isgiven;
        this.difficulty = difficulty;
        this.quid = quid;
        this.start = start;
        this.end = end;
        this.date = date;
        this.submittime = submittime;
        this.isresultvisible = isresultvisible;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getQuestioncount() {
        return questioncount;
    }

    public void setQuestioncount(String questioncount) {
        this.questioncount = questioncount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUserscore() {
        return userscore;
    }

    public void setUserscore(String userscore) {
        this.userscore = userscore;
    }

    public String getIsgiven() {
        return isgiven;
    }

    public void setIsgiven(String isgiven) {
        this.isgiven = isgiven;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuid() {
        return quid;
    }

    public void setQuid(String quid) {
        this.quid = quid;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getIsresultvisible() {
        return isresultvisible;
    }

    public void setIsresultvisible(String isresultvisible) {
        this.isresultvisible = isresultvisible;
    }
}
