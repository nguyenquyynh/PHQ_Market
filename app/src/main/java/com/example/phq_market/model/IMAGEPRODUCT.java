package com.example.phq_market.model;

public class IMAGEPRODUCT {
    private Integer ID;
    private Integer IDPRODUCT;
    private String IMG;

    public IMAGEPRODUCT() {
    }

    public IMAGEPRODUCT(Integer ID, Integer IDPRODUCT, String IMG) {
        this.ID = ID;
        this.IDPRODUCT = IDPRODUCT;
        this.IMG = IMG;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
}
