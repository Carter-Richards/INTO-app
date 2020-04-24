package com.Team25.intoapp;

import android.graphics.Bitmap;

/** A basic class with setters and getters to contain information about information entities with images.
 */
public class BitmapInformationObject {
    private String title;
    private String description;
    private String location;
    private Bitmap img;
    private String date;

    public BitmapInformationObject(String title, String description, String location, Bitmap img, String date) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.img = img;
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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
