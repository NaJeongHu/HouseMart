package com.example.publicdatacompetition.Model;

import android.app.Application;

import java.io.Serializable;

public class User  implements Serializable {

    private Long id;
    private String userId;
    private String firebaseId; // 파베 아이디
    private String phoneNumber; // 핸드폰 번호
    private String name; // 성명
    private String nickname; // 닉네임
    private String imgUrl; // 이미지 url
    private String idNum; // 주민번호

    public User() {}

    public User(Long id, String userId, String firebaseId, String phoneNumber, String name, String nickname, String imgUrl, String idNum) {
        this.id = id;
        this.userId = userId;
        this.firebaseId = firebaseId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.idNum = idNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
}
