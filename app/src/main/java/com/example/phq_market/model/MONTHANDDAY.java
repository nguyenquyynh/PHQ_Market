package com.example.phq_market.model;

public class MONTHANDDAY {
    private Integer MONTH;
    private Float PAY;

    public MONTHANDDAY(Integer MONTH, Float PAY) {
        this.MONTH = MONTH;
        this.PAY = PAY;
    }

    public Integer getMONTH() {
        return MONTH;
    }

    public void setMONTH(Integer MONTH) {
        this.MONTH = MONTH;
    }

    public Float getPAY() {
        return PAY;
    }

    public void setPAY(Float PAY) {
        this.PAY = PAY;
    }
}
