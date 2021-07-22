package com.example.publicdatacompetition.Model;

public class Apartment {
    private String userId;//사용자 이름
    private String aparmentName;//아파트 이름
    private Integer dong,ho;//동,호수
    private Integer net_leaseable_area;//전용면적
    private Integer leaseable_area;//공급면적


    private String type;
    private Long sale_price;//매매가
    private Long monthly_price;//월세
    private Long monthly_deposit;//보증금
    private Long deposit;//전세금
    private Long admin_expenses;//관리비

    private Integer provisional_down_pay_per;//가계약금 비율
    private Integer down_pay_per;//계약금 비율
    private Integer intermediate_pay_per;//중도금 비율
    private Integer balence_per;//잔금 비율

    private boolean middle_door;//중문
    private boolean air_conditioner;//에어컨
    private boolean refrigerator;//냉장고
    private boolean kimchi_refrigerator;//김치냉장고
    private boolean closet;//붙박이장

    private boolean nego;//네고가능

    private String short_descriptioin;//짧은 집 소개
    private String long_description;//긴 집 소개

    public Apartment(){

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAparmentName(String aparmentName) {
        this.aparmentName = aparmentName;
    }

    public void setDong(Integer dong) {
        this.dong = dong;
    }

    public void setHo(Integer ho) {
        this.ho = ho;
    }

    public void setNet_leaseable_area(Integer net_leaseable_area) {
        this.net_leaseable_area = net_leaseable_area;
    }

    public void setLeaseable_area(Integer leaseable_area) {
        this.leaseable_area = leaseable_area;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSale_price(Long sale_price) {
        this.sale_price = sale_price;
    }

    public void setMonthly_price(Long monthly_price) {
        this.monthly_price = monthly_price;
    }

    public void setMonthly_deposit(Long monthly_deposit) {
        this.monthly_deposit = monthly_deposit;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public void setAdmin_expenses(Long admin_expenses) {
        this.admin_expenses = admin_expenses;
    }

    public void setProvisional_down_pay_per(Integer provisional_down_pay_per) {
        this.provisional_down_pay_per = provisional_down_pay_per;
    }

    public void setDown_pay_per(Integer down_pay_per) {
        this.down_pay_per = down_pay_per;
    }

    public void setIntermediate_pay_per(Integer intermediate_pay_per) {
        this.intermediate_pay_per = intermediate_pay_per;
    }

    public void setBalence_per(Integer balence_per) {
        this.balence_per = balence_per;
    }

    public void setMiddle_door(boolean middle_door) {
        this.middle_door = middle_door;
    }

    public void setAir_conditioner(boolean air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    public void setRefrigerator(boolean refrigerator) {
        this.refrigerator = refrigerator;
    }

    public void setKimchi_refrigerator(boolean kimchi_refrigerator) {
        this.kimchi_refrigerator = kimchi_refrigerator;
    }

    public void setCloset(boolean closet) {
        this.closet = closet;
    }

    public void setNego(boolean nego) {
        this.nego = nego;
    }

    public void setShort_descriptioin(String short_descriptioin) {
        this.short_descriptioin = short_descriptioin;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }
}

