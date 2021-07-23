package com.example.publicdatacompetition;

import java.io.Serializable;

public class PermittedHouse implements Serializable {

    private int idx, dong, ho, leaseable_area, sale_price, monthly_price, monthly_deposit, deposit;
    private String residence_name, type, titleImg, residence_type;

    public String getResidence_name() {
        return residence_name;
    }

    public void setResidence_name(String residence_name) {
        this.residence_name = residence_name;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getDong() {
        return dong;
    }

    public void setDong(int dong) {
        this.dong = dong;
    }

    public int getHo() {
        return ho;
    }

    public void setHo(int ho) {
        this.ho = ho;
    }

    public int getLeaseable_area() {
        return leaseable_area;
    }

    public void setLeaseable_area(int leaseable_area) {
        this.leaseable_area = leaseable_area;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }

    public int getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(int monthly_price) {
        this.monthly_price = monthly_price;
    }

    public int getMonthly_deposit() {
        return monthly_deposit;
    }

    public void setMonthly_deposit(int monthly_deposit) {
        this.monthly_deposit = monthly_deposit;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}
