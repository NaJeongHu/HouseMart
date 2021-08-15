package com.howsmart.housemart.Model;

import android.net.Uri;

import java.io.Serializable;

public class Pictures implements Serializable {
    private String type;
    private int image;
    private Uri uri;
    private String description;
    private String guide;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Pictures(String type, int image, String guide, String title){
        this.type=type;
        this.image=image;
        this.guide=guide;
        this.title=title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
