package com.segmnf.myapplication.Model;

public class ActivityModel {
    String type; String name; String score;

    public ActivityModel() {
    }

    public ActivityModel( String name,String type, String score) {
        this.type = type;
        this.name = name;
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
