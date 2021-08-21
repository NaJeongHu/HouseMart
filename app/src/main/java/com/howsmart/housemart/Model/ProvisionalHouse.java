package com.howsmart.housemart.Model;

import java.io.Serializable;

public class ProvisionalHouse implements Serializable {
    private int dong, ho;
    private double leaseable_area;
    private long idx, sale_price, monthly_price;
    private String residence_name, residence_type;
    private String sido;//시도
    private String sigungoo;//시군구
    private String sale_type;
    private String dongri;

    public ProvisionalHouse(int dong, int ho, double leaseable_area, long idx, long sale_price, long monthly_price, String residence_name, String residence_type, String sido, String sigungoo, String sale_type, String dongri) {
        this.dong = dong;
        this.ho = ho;
        this.leaseable_area = leaseable_area;
        this.idx = idx;
        this.sale_price = sale_price;
        this.monthly_price = monthly_price;
        this.residence_name = residence_name;
        this.residence_type = residence_type;
        this.sido = sido;
        this.sigungoo = sigungoo;
        this.sale_type = sale_type;
        this.dongri = dongri;
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

    public double getLeaseable_area() {
        return leaseable_area;
    }

    public void setLeaseable_area(double leaseable_area) {
        this.leaseable_area = leaseable_area;
    }

    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public long getSale_price() {
        return sale_price;
    }

    public void setSale_price(long sale_price) {
        this.sale_price = sale_price;
    }

    public long getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(long monthly_price) {
        this.monthly_price = monthly_price;
    }

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

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getSigungoo() {
        return sigungoo;
    }

    public void setSigungoo(String sigungoo) {
        this.sigungoo = sigungoo;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public String getDongri() {
        return dongri;
    }

    public void setDongri(String dongri) {
        this.dongri = dongri;
    }
}
