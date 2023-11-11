package com.example.phq_market.model;

import java.io.Serializable;

public class NEWPRODUCT implements Serializable {
    private Integer ID;
    private String NAME;
    private Float PRICE;
    private Double EVALUATE;
    private String IMG;

    public NEWPRODUCT(Integer ID, String NAME, Float PRICE, Double EVALUATE, String IMG
    ) {
        this.ID = ID;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.EVALUATE = EVALUATE;
        this.IMG = IMG;
    }

    public NEWPRODUCT() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Float getPRICE() {
        return PRICE;
    }

    public void setPRICE(Float PRICE) {
        this.PRICE = PRICE;
    }

    public Double getEVALUATE() {
        return EVALUATE;
    }

    public void setEVALUATE(Double EVALUATE) {
        this.EVALUATE = EVALUATE;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }
}
