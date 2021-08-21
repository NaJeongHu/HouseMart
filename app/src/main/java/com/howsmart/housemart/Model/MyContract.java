package com.howsmart.housemart.Model;

public class MyContract {
    private String sale_type;//전세/매매/월세 타입
    private String residence_name, titleImg, residence_type;
    private Long idx;
    private Integer dong, ho;
    private String dongri;//동리
    private double leaseable_area;

    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세

    private String createDate;//계약날짜
    private String sellerName, buyerName;//매도인,매수인의 이름
    private String offerState;

    public MyContract(){}

    public double getLeaseable_area() {
        return leaseable_area;
    }

    public Integer getDong() {
        return dong;
    }

    public Integer getHo() {
        return ho;
    }

    public String getSale_type() {
        return sale_type;
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

    public Long getIdx() {
        return idx;
    }

    public String getDongri() {
        return dongri;
    }

    public Long getSale_price() {
        return sale_price;
    }

    public Long getMonthly_price() {
        return monthly_price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getOfferState() {
        return offerState;
    }
}