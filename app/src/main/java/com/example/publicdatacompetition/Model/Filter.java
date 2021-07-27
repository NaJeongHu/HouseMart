package com.example.publicdatacompetition.Model;

public class Filter {
    private boolean S, M, C;//가격 유형 (S : 매매, M : 월세, C : 전세)
    private long guarantee_start;//전세금 최소
    private long guarantee_end;//전세금 최대  //-1일 경우 무제한
    private long monthly_start;//월세 최소
    private long monthly_end;//월세 최대 //-1일 경우 무제한
    private long sale_start;//매매가 최소
    private long sale_end;//매매가 최대 //-1일 경우 무제한
    private double area_start;//면적 최소
    private double area_end;//면적 최대  //-1일 경우 무제한
    private int year;//준공일로부터 년도 // -1일 경우 15년 이상 선택
    private int park;//주차 가능 대수

    public Filter(boolean s, boolean m, boolean c, long guarantee_start, long guarantee_end, long monthly_start, long monthly_end, long sale_start, long sale_end, double area_start, double area_end, int year, int park) {
        S = s;
        M = m;
        C = c;
        this.guarantee_start = guarantee_start;
        this.guarantee_end = guarantee_end;
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


