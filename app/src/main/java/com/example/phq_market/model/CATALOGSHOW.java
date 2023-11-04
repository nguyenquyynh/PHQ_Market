package com.example.phq_market.model;

public class CATALOGSHOW {
    private Integer ID;
    private String NAME;
    private String IMG;
    private Integer QUANTITY;

    public CATALOGSHOW() {
    }

    public CATALOGSHOW(Integer ID, String NAME, String IMG, Integer QUANTITY) {
        this.ID = ID;
        this.NAME = NAME;
        this.IMG = IMG;
        this.QUANTITY = QUANTITY;
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

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }
}
