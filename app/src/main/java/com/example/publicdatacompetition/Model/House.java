package com.example.publicdatacompetition.Model;

public class House {
    private String userId;//사용자 이름
    private String residence_name;//아파트 이름

    private String code;//아파트 코드
    private Integer dong, ho;//동,호수
    private Double net_leaseable_area;//전용면적
    private Double leaseable_area;//공급면적

    private String residence_type;//매물 타입(A,V,O)
    private String sale_type;//"월세","전세","매매"
    private Long sale_price;//매매가/전세금/보증금
    private Long monthly_price;//월세
    private Long admin_expenses;//관리비

    private Integer provisional_down_pay_per;//가계약금 비율
    private Integer down_pay_per;//계약금 비율
    private Integer intermediate_pay_per;//중도금 비율
    private Integer balence_per;//잔금 비율

    private Integer room_num;//방 개수
    private Integer toilet_num;//욕실 개수

    private boolean middle_door;//중문
    private boolean air_conditioner;//시스템 에어컨
    private boolean refrigerator;//냉장고
    private boolean kimchi_refrigerator;//김치냉장고
    private boolean closet;//붙박이장
    private boolean oven;//빌트인 오븐
    private boolean induction;//인덕션
    private boolean airsystem;//공조기 시스템

    private boolean nego;//네고가능

    private String short_descriptioin;//짧은 집 소개
    private String long_description;//긴 집 소개
    private String apartment_descriptioin;//아파트 소개
    private String livingroom_descriptioin;//거실 소개
    private String kitchen_description;//주방 소개
    private String room1_descriptioin;//방1 소개
    private String room2_description;//방2 소개
    private String room3_descriptioin;//방3 소개
    private String toilet1_description;//화장실1 소개
    private String toilet2_description;//화장실2 소개

    private String movedate;//입주가능일

    public House() {

    }

    public House(String userId, String residence_name, String code, Integer dong, Integer ho, Double net_leaseable_area, Double leaseable_area, String residence_type, String sale_type, Long sale_price, Long monthly_price, Long admin_expenses, Integer provisional_down_pay_per, Integer down_pay_per, Integer intermediate_pay_per, Integer balence_per, Integer room_num, Integer toilet_num, boolean middle_door, boolean air_conditioner, boolean refrigerator, boolean kimchi_refrigerator, boolean closet, boolean oven, boolean induction, boolean airsystem, boolean nego, String short_descriptioin, String long_description, String apartment_descriptioin, String livingroom_descriptioin, String kitchen_description, String room1_descriptioin, String room2_description, String room3_descriptioin, String toilet1_description, String toilet2_description, String movedate) {
        this.userId = userId;
        this.residence_name = residence_name;
        this.code = code;
        this.dong = dong;
        this.ho = ho;
        this.net_leaseable_area = net_leaseable_area;
        this.leaseable_area = leaseable_area;
        this.residence_type = residence_type;
        this.sale_type = sale_type;
        this.sale_price = sale_price;
        this.monthly_price = monthly_price;
        this.admin_expenses = admin_expenses;
        this.provisional_down_pay_per = provisional_down_pay_per;
        this.down_pay_per = down_pay_per;
        this.intermediate_pay_per = intermediate_pay_per;
        this.balence_per = balence_per;
        this.room_num = room_num;
        this.toilet_num = toilet_num;
        this.middle_door = middle_door;
        this.air_conditioner = air_conditioner;
        this.refrigerator = refrigerator;
        this.kimchi_refrigerator = kimchi_refrigerator;
        this.closet = closet;
        this.oven = oven;
        this.induction = induction;
        this.airsystem = airsystem;
        this.nego = nego;
        this.short_descriptioin = short_descriptioin;
        this.long_description = long_description;
        this.apartment_descriptioin = apartment_descriptioin;
        this.livingroom_descriptioin = livingroom_descriptioin;
        this.kitchen_description = kitchen_description;
        this.room1_descriptioin = room1_descriptioin;
        this.room2_description = room2_description;
        this.room3_descriptioin = room3_descriptioin;
        this.toilet1_description = toilet1_description;
        this.toilet2_description = toilet2_description;
        this.movedate = movedate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResidence_name() {
        return residence_name;
    }

    public void setResidence_name(String residence_name) {
        this.residence_name = residence_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDong() {
        return dong;
    }

    public void setDong(Integer dong) {
        this.dong = dong;
    }

    public Integer getHo() {
        return ho;
    }

    public void setHo(Integer ho) {
        this.ho = ho;
    }

    public Double getNet_leaseable_area() {
        return net_leaseable_area;
    }

    public void setNet_leaseable_area(Double net_leaseable_area) {
        this.net_leaseable_area = net_leaseable_area;
    }

    public Double getLeaseable_area() {
        return leaseable_area;
    }

    public void setLeaseable_area(Double leaseable_area) {
        this.leaseable_area = leaseable_area;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
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

    public Long getAdmin_expenses() {
        return admin_expenses;
    }

    public void setAdmin_expenses(Long admin_expenses) {
        this.admin_expenses = admin_expenses;
    }

    public Integer getProvisional_down_pay_per() {
        return provisional_down_pay_per;
    }

    public void setProvisional_down_pay_per(Integer provisional_down_pay_per) {
        this.provisional_down_pay_per = provisional_down_pay_per;
    }

    public Integer getDown_pay_per() {
        return down_pay_per;
    }

    public void setDown_pay_per(Integer down_pay_per) {
        this.down_pay_per = down_pay_per;
    }

    public Integer getIntermediate_pay_per() {
        return intermediate_pay_per;
    }

    public void setIntermediate_pay_per(Integer intermediate_pay_per) {
        this.intermediate_pay_per = intermediate_pay_per;
    }

    public Integer getBalence_per() {
        return balence_per;
    }

    public void setBalence_per(Integer balence_per) {
        this.balence_per = balence_per;
    }

    public Integer getRoom_num() {
        return room_num;
    }

    public void setRoom_num(Integer room_num) {
        this.room_num = room_num;
    }

    public Integer getToilet_num() {
        return toilet_num;
    }

    public void setToilet_num(Integer toilet_num) {
        this.toilet_num = toilet_num;
    }

    public boolean isMiddle_door() {
        return middle_door;
    }

    public void setMiddle_door(boolean middle_door) {
        this.middle_door = middle_door;
    }

    public boolean isAir_conditioner() {
        return air_conditioner;
    }

    public void setAir_conditioner(boolean air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    public boolean isRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(boolean refrigerator) {
        this.refrigerator = refrigerator;
    }

    public boolean isKimchi_refrigerator() {
        return kimchi_refrigerator;
    }

    public void setKimchi_refrigerator(boolean kimchi_refrigerator) {
        this.kimchi_refrigerator = kimchi_refrigerator;
    }

    public boolean isCloset() {
        return closet;
    }

    public void setCloset(boolean closet) {
        this.closet = closet;
    }

    public boolean isOven() {
        return oven;
    }

    public void setOven(boolean oven) {
        this.oven = oven;
    }

    public boolean isInduction() {
        return induction;
    }

    public void setInduction(boolean induction) {
        this.induction = induction;
    }

    public boolean isAirsystem() {
        return airsystem;
    }

    public void setAirsystem(boolean airsystem) {
        this.airsystem = airsystem;
    }

    public boolean isNego() {
        return nego;
    }

    public void setNego(boolean nego) {
        this.nego = nego;
    }

    public String getShort_descriptioin() {
        return short_descriptioin;
    }

    public void setShort_descriptioin(String short_descriptioin) {
        this.short_descriptioin = short_descriptioin;
    }

    public String getLong_description() {
        return long_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public String getApartment_descriptioin() {
        return apartment_descriptioin;
    }

    public void setApartment_descriptioin(String apartment_descriptioin) {
        this.apartment_descriptioin = apartment_descriptioin;
    }

    public String getLivingroom_descriptioin() {
        return livingroom_descriptioin;
    }

    public void setLivingroom_descriptioin(String livingroom_descriptioin) {
        this.livingroom_descriptioin = livingroom_descriptioin;
    }

    public String getKitchen_description() {
        return kitchen_description;
    }

    public void setKitchen_description(String kitchen_description) {
        this.kitchen_description = kitchen_description;
    }

    public String getRoom1_descriptioin() {
        return room1_descriptioin;
    }

    public void setRoom1_descriptioin(String room1_descriptioin) {
        this.room1_descriptioin = room1_descriptioin;
    }

    public String getRoom2_description() {
        return room2_description;
    }

    public void setRoom2_description(String room2_description) {
        this.room2_description = room2_description;
    }

    public String getRoom3_descriptioin() {
        return room3_descriptioin;
    }

    public void setRoom3_descriptioin(String room3_descriptioin) {
        this.room3_descriptioin = room3_descriptioin;
    }

    public String getToilet1_description() {
        return toilet1_description;
    }

    public void setToilet1_description(String toilet1_description) {
        this.toilet1_description = toilet1_description;
    }

    public String getToilet2_description() {
        return toilet2_description;
    }

    public void setToilet2_description(String toilet2_description) {
        this.toilet2_description = toilet2_description;
    }

    public String getMovedate() {
        return movedate;
    }

    public void setMovedate(String movedate) {
        this.movedate = movedate;
    }
}
