package com.segmnf.myapplication.Model;

public class QuestionModel {
    String contestid;
    String name;
    String avgtime;
    String difficulty;
    String marks;
    String description;
    String eg1;
    String eg2;
    String constraints;
    String testcases;
    String testoutputs;
    String cpu;
    String memory;
    String status;
    String quid;

    public QuestionModel(String contestid, String name, String avgtime, String difficulty, String marks, String description, String eg1, String eg2, String constraints, String testcases, String testoutputs, String cpu, String memory, String status, String quid) {
        this.contestid = contestid;
        this.name = name;
        this.avgtime = avgtime;
        this.difficulty = difficulty;
        this.marks = marks;
        this.description = description;
        this.eg1 = eg1;
        this.eg2 = eg2;
        this.constraints = constraints;
        this.testcases = testcases;
        this.testoutputs = testoutputs;
        this.cpu = cpu;
        this.memory = memory;
        this.status = status;
        this.quid= quid;
    }

    public QuestionModel() {
    }

    public String getQuid() {
        return quid;
    }

    public void setQuid(String quid) {
        this.quid = quid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContestid() {
        return contestid;
    }

    public void setContestid(String contestid) {
        this.contestid = contestid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(String avgtime) {
        this.avgtime = avgtime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEg1() {
        return eg1;
    }

    public void setEg1(String eg1) {
        this.eg1 = eg1;
    }

    public String getEg2() {
        return eg2;
    }

    public void setEg2(String eg2) {
        this.eg2 = eg2;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getTestcases() {
        return testcases;
    }

    public void setTestcases(String testcases) {
        this.testcases = testcases;
    }

    public String getTestoutputs() {
        return testoutputs;
    }

    public void setTestoutputs(String testoutputs) {
        this.testoutputs = testoutputs;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
