package com.example.phq_market.model;

public class CATALOG {
    private Integer ID;
    private String NAME;
    private String IMG;

    public CATALOG() {
    }

    public CATALOG(Integer ID, String NAME, String IMG) {
        this.ID = ID;
        this.NAME = NAME;
        this.IMG = IMG;
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
}
