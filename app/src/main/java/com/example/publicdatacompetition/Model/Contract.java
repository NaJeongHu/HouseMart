package com.example.publicdatacompetition.Model;

public class Contract {
    private String sale_type;//전세/매매/월세 타입
    private String address_apartment;//도로명 주소 + 아파트 이름
    private String purpose;//아파트 용도
    private String area;//전용면적/공급면적

    // String으로 10000000원(1천만원)으로 형식 맞춤
    private String sale_prices;//매매가/전세금/보증금
    private String monthly_prices;//월세
    private String provisional_down_pay;//가계약금
    private String down_pay;//계약금
    private String intermediate_pay;//중도금
    private String balance;//잔금

    private String special;//특약 사항
    private String date;//오늘 날짜
    private String name1, name2;//매도인,매수인의 이름
    private String birth1, birth2;//생년월일
    private String phonenumber1, phonenumber2;//전화번호
    private Long id1,id2;//id1 = 매도자 아이디, id2 = 매수자 아이디

    private Boolean editable;//수정가능여부

    public Contract(String type, String address_apartment, String purpose, String area, String sale_prices, String monthly_prices, String provisional_down_pay, String down_pay, String intermediate_pay, String balance, String special, String date, String name1, String name2, String birth1, String birth2, String phonenumber1, String phonenumber2, Long id1, Long id2, Boolean editable) {
        this.sale_type = type;
        this.address_apartment = address_apartment;
        this.purpose = purpose;
        this.area = area;
        this.sale_prices = sale_prices;
        this.monthly_prices = monthly_prices;
        this.provisional_down_pay = provisional_down_pay;
        this.down_pay = down_pay;
        this.intermediate_pay = intermediate_pay;
        this.balance = balance;
        this.special = special;
        this.date = date;
        this.name1 = name1;
        this.name2 = name2;
        this.birth1 = birth1;
        this.birth2 = birth2;
        this.phonenumber1 = phonenumber1;
        this.phonenumber2 = phonenumber2;
        this.id1 = id1;
        this.id2 = id2;
        this.editable = editable;
    }

    public Boolean getEditable() {
        return editable;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public String getAddress_apartment() {
        return address_apartment;
    }

    public void setAddress_apartment(String address_apartment) {
        this.address_apartment = address_apartment;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSale_prices() {
        return sale_prices;
    }

    public void setSale_prices(String sale_prices) {
        this.sale_prices = sale_prices;
    }

    public String getMonthly_prices() {
        return monthly_prices;
    }

    public void setMonthly_prices(String monthly_prices) {
        this.monthly_prices = monthly_prices;
    }

    public String getProvisional_down_pay() {
        return provisional_down_pay;
    }

    public void setProvisional_down_pay(String provisional_down_pay) {
        this.provisional_down_pay = provisional_down_pay;
    }

    public String getDown_pay() {
        return down_pay;
    }

    public void setDown_pay(String down_pay) {
        this.down_pay = down_pay;
    }

    public String getIntermediate_pay() {
        return intermediate_pay;
    }

    public void setIntermediate_pay(String intermediate_pay) {
        this.intermediate_pay = intermediate_pay;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getBirth1() {
        return birth1;
    }

    public void setBirth1(String birth1) {
        this.birth1 = birth1;
    }

    public String getBirth2() {
        return birth2;
    }

    public void setBirth2(String birth2) {
        this.birth2 = birth2;
    }

    public String getPhonenumber1() {
        return phonenumber1;
    }

    public void setPhonenumber1(String phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }
}