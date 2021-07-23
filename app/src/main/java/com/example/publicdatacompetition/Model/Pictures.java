package com.example.publicdatacompetition.Model;

import android.net.Uri;

public class Pictures {
    private String type;
    private int image;
    private Uri uri;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Pictures(String type, int image){
        this.type=type;
        this.image=image;
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
