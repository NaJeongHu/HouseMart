package com.howsmart.housemart.Model;

public class MarketPrice {

    private String contract_date; //계약일 ex)21.07.22
    private String sale_type; //"월세", "전세", "매매"
    private Long sale_price; //매매가/전세금/보증금 ex)12000(만원) -> 1억 2,000
    private Long monthly_price; //월세 ex)18000(만원) -> 1억 8,000
    private Double net_leaseable_area; //전용면적 ex)133.12 -> 정수
    private int floor; //층수 ex)2

    public MarketPrice(String contract_date, String sale_type, Long sale_price, Long monthly_price, Double net_leaseable_area, int floor) {
        this.contract_date = contract_date;
        this.sale_type = sale_type;
        this.sale_price = sale_price;
        this.monthly_price = monthly_price;
        this.net_leaseable_area = net_leaseable_area;
        this.floor = floor;
    }

    public MarketPrice() {
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

    public Long getSale_price() {
        return sale_price;
    }

    public void setSale_price(Long sale_price) {
        this.sale_price = sale_price;
    }

    public Long getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(Long monthly_price) {
        this.monthly_price = monthly_price;
    }

    public Double getNet_leaseable_area() {
        return net_leaseable_area;
    }

    public void setNet_leaseable_area(Double net_leaseable_area) {
        this.net_leaseable_area = net_leaseable_area;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
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
