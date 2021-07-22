package com.example.publicdatacompetition;

import java.io.Serializable;

public class MyLocation implements Serializable {

    private double LATITUDE, LONGITUDE;
    private String SIDO, SIGUNGU;

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getSIDO() {
        return SIDO;
    }

    public void setSIDO(String SIDO) {
        this.SIDO = SIDO;
    }

    public String getSIGUNGU() {
        return SIGUNGU;
    }

    public void setSIGUNGU(String SIGUNGU) {
        this.SIGUNGU = SIGUNGU;
    }

    private static MyLocation instance = null;

    public static synchronized MyLocation getInstance() {
        if (instance == null) {
            instance = new MyLocation();
        }
        return instance;
    }

}
