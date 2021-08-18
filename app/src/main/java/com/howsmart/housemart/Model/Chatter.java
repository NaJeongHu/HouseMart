package com.howsmart.housemart.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Chatter implements Serializable {
    String id;
    String username;
    String nickname;
    String phone;
    String imageURL;
    String search;
    String token;

    public Chatter(String id, String username, String nickname, String phone, String imageURL, String search, String token) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.phone = phone;
        this.imageURL = imageURL;
        this.search = search;
        this.token = token;
    }

    public Chatter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Chatter{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", search='" + search + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
