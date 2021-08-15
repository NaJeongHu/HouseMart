package com.howsmart.housemart.Model;

import java.io.Serializable;

public class Filter implements Serializable {
    private String type;//"월세","전세","매매" 유형
    private long monthly_start;//월세 최소
    private long monthly_end;//월세 최대 //-1일 경우 무제한
    private long sale_start;//매매금/전세금/보증금 최소
    private long sale_end;//매매금/전세금/보증금 최대 //-1일 경우 무제한
    private double area_start;//면적 최소
    private double area_end;//면적 최대  //-1일 경우 무제한
    private int year;//준공일로부터 년도 // -1일 경우 15년 이상 선택
    private int park;//주차 가능 대수

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMonthly_start() {
        return monthly_start;
    }

    public void setMonthly_start(long monthly_start) {
        this.monthly_start = monthly_start;
    }

    public long getMonthly_end() {
        return monthly_end;
    }

    public void setMonthly_end(long monthly_end) {
        this.monthly_end = monthly_end;
    }

    public long getSale_start() {
        return sale_start;
    }

    public void setSale_start(long sale_start) {
        this.sale_start = sale_start;
    }

    public long getSale_end() {
        return sale_end;
    }

    public void setSale_end(long sale_end) {
        this.sale_end = sale_end;
    }

    public double getArea_start() {
        return area_start;
    }

    public void setArea_start(double area_start) {
        this.area_start = area_start;
    }

    public double getArea_end() {
        return area_end;
    }

    public void setArea_end(double area_end) {
        this.area_end = area_end;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPark() {
        return park;
    }

    public void setPark(int park) {
        this.park = park;
    }

    public Filter(String type, long monthly_start, long monthly_end, long sale_start, long sale_end, double area_start, double area_end, int year, int park) {
        this.type = type;
        this.monthly_start = monthly_start;
        this.monthly_end = monthly_end;
        this.sale_start = sale_start;
        this.sale_end = sale_end;
        this.area_start = area_start;
        this.area_end = area_end;
        this.year = year;
        this.park = park;
    }
}


