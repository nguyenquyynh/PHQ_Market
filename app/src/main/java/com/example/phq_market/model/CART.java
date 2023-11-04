package com.example.phq_market.model;

public class CART {
    private Integer ID;
    private String NAME;
    private Float PRICE;
    private  Integer QUANTITY;
    private String Image;

    public CART(Integer ID, String NAME, Float PRICE, Integer QUANTITY, String image) {
        this.ID = ID;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.QUANTITY = QUANTITY;
        this.Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
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
