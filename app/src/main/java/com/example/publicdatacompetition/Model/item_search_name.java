package com.example.publicdatacompetition.Model;

public class item_search_name {
    private String code;
    private String name;
    private String address;
    private String sido;
    private String sigungoo;
    private String dongri;
    private String date;
    private String allnumber;
    private String parkingnumber;
    private String contact;
    private int start;//
    private int end;

    public item_search_name() {

    }

    public item_search_name(String code, String name, String address, String sido, String sigungoo, String dongri, String date, String allnumber, String parkingnumber, String contact, int start, int end) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.sido = sido;
        this.sigungoo = sigungoo;
        this.dongri = dongri;
        this.date = date;
        this.allnumber = allnumber;
        this.parkingnumber = parkingnumber;
        this.contact = contact;
        this.start = start;
        this.end = end;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDongri() {
        return dongri;
    }

    public void setDongri(String dongri) {
        this.dongri = dongri;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAllnumber() {
        return allnumber;
    }

    public void setAllnumber(String allnumber) {
        this.allnumber = allnumber;
    }

    public String getParkingnumber() {
        return parkingnumber;
    }

    public void setParkingnumber(String parkingnumber) {
        this.parkingnumber = parkingnumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

