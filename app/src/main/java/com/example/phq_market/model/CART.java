package com.example.phq_market.model;

public class CART {
    private Integer ID;
    private String NAME;
    private Float PRICE;
    private  Integer QUANTITY;
    private String IMG;

    public CART(Integer ID, String NAME, Float PRICE, Integer QUANTITY, String IMG) {
        this.ID = ID;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.QUANTITY = QUANTITY;
        this.IMG = IMG;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        IMG = IMG;
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


    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }
}
