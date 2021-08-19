package com.howsmart.housemart.Model;

public class Realprice {

    private String contract_date; //계약일 ex)21.07.22
    private String sale_type; //"월세", "전세", "매매"
    private String sale_price; //매매가/전세금/보증금 ex)12000(만원) -> 1억 2,000
    private String monthly_price; //월세 ex)18000(만원) -> 1억 8,000
    private String net_leaseable_area; //전용면적 ex)133.12 -> 정수
    private String floor; //층수 ex)2

    public Realprice(String contract_date, String sale_type, String sale_price, String monthly_price, String net_leaseable_area, String floor) {
        this.contract_date = contract_date;
        this.sale_type = sale_type;
        this.sale_price = sale_price;
        this.monthly_price = monthly_price;
        this.net_leaseable_area = net_leaseable_area;
        this.floor = floor;
    }

    public Realprice(){

    }

    public String getContract_date() {
        return contract_date;
    }

    public void setContract_date(String contract_date) {
        this.contract_date = contract_date;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(String monthly_price) {
        this.monthly_price = monthly_price;
    }

    public String getNet_leaseable_area() {
        return net_leaseable_area;
    }

    public void setNet_leaseable_area(String net_leaseable_area) {
        this.net_leaseable_area = net_leaseable_area;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "MarketPrice{" +
                "contract_date='" + contract_date + '\'' +
                ", sale_type='" + sale_type + '\'' +
                ", sale_price=" + sale_price +
                ", monthly_price=" + monthly_price +
                ", net_leaseable_area=" + net_leaseable_area +
                ", floor=" + floor +
                '}';
    }
}
