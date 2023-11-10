package com.example.phq_market.model;

public class CHECKOUT {
    private Integer IDPRODUCT;
    private String IMG;
    private String NAME;
    private Float PRICE;
    private Integer QUANTITY;

    public CHECKOUT(Integer IDPRODUCT, String IMG, String NAME, Float PRICE, Integer QUANTITY) {
        this.IDPRODUCT = IDPRODUCT;
        this.IMG = IMG;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.QUANTITY = QUANTITY;
    }

    public Integer getIDPRODUCT() {
        return IDPRODUCT;
    }

    public void setIDPRODUCT(Integer IDPRODUCT) {
        this.IDPRODUCT = IDPRODUCT;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
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
