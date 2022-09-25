package com.segmnf.myapplication.Model;

public class NewsModel {

    private String title, description, content, author, newsImage, date ;

    public NewsModel() {
    }

    public NewsModel(String title, String description, String content, String author, String newsImage, String date) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.author = author;
        this.newsImage = newsImage;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
