package com.example.publicdatacompetition.Model;

import android.app.Application;

public class Chatter extends Application {
    String id;
    String nickname;
    String phone;
    String imageURL;
    String search;

    public Chatter(String id, String nickname, String phone, String imageURL, String search) {
        this.id = id;
        this.nickname = nickname;
        this.phone = phone;
        this.imageURL = imageURL;
        this.search = search;
    }

    public Chatter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
