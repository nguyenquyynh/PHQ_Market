package com.example.phq_market.model;

public class PRODUCTDETAIL {
    private Integer ID;
    private String NAME;
    private String DESCRIBED;
    private Float PRICE;
    private Double EVALUATE;

    public PRODUCTDETAIL() {
    }

    public PRODUCTDETAIL(Integer ID, String NAME, String DESCRIBED, Float PRICE, Double EVALUATE) {
        this.ID = ID;
        this.NAME = NAME;
        this.DESCRIBED = DESCRIBED;
        this.PRICE = PRICE;
        this.EVALUATE = EVALUATE;
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

    public String getDESCRIBED() {
        return DESCRIBED;
    }

    public void setDESCRIBED(String DESCRIBED) {
        this.DESCRIBED = DESCRIBED;
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
}
