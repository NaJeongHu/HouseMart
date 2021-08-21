package com.howsmart.housemart.Model;

import java.io.Serializable;

public class MyHouse implements Serializable {

    private int idx, dong, ho;
    private Long sale_price, monthly_price;
    private double leaseable_area;
    private String residence_name, titleImg, residence_type;
    private String dongri;//동리
    private String sale_type;//"월세","전세","매매"
    private String offerState;
    private String createDate;

    public MyHouse() {
    }

    public int getIdx() {
        return idx;
    }

    public int getDong() {
        return dong;
    }

    public int getHo() {
        return ho;
    }

    public Long getSale_price() {
        return sale_price;
    }

    public Long getMonthly_price() {
        return monthly_price;
    }

    public double getLeaseable_area() {
        return leaseable_area;
    }

    public String getResidence_name() {
        return residence_name;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public String getDongri() {
        return dongri;
    }

    public String getSale_type() {
        return sale_type;
    }

    public String getOfferState() {
        return offerState;
    }

    public String getCreateDate() {
        return createDate;
    }
}
