package com.Team25.intoapp;

public class InformationObject {
    private String title;
    private String description;
    private String location;
    private String imgPath;
    private String date;
    public InformationObject(String title, String description, String location, String imgPath, String date) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.imgPath = imgPath;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
