package com.example.publicdatacompetition.Model;

public class item_search_name {
    private String code;
    private String name;
    private String address;
    private int start;
    private int end;

    public item_search_name(){

    }

    public item_search_name(String code, String name, String address,int start, int end) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.start = start;
        this.end = end;
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
}
