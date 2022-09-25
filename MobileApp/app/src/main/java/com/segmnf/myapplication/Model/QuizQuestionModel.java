package com.segmnf.myapplication.Model;

public class QuizQuestionModel {

    String adminid;
    String quizid;
    String questionid;
    String marks;
    String question;
    String image;
    String op1;
    String op2;
    String op3;
    String op4;
    String correctop;

    public QuizQuestionModel() {
    }

    public QuizQuestionModel(String adminid, String quizid, String questionid, String marks, String question, String image, String op1, String op2, String op3, String op4, String correctop) {
        this.adminid = adminid;
        this.quizid = quizid;
        this.questionid = questionid;
        this.marks = marks;
        this.question = question;
        this.image = image;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
        this.correctop = correctop;
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

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    public String getCorrectop() {
        return correctop;
    }

    public void setCorrectop(String correctop) {
        this.correctop = correctop;
    }
}
