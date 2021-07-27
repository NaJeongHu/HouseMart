package com.example.publicdatacompetition.Model;

import android.app.Application;

public class User extends Application {
    String id;
    String name;
    String nickname;
    String idnum;

    public User(String id, String name, String nickname, String idnum) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.idnum = idnum;
    }

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }
}
