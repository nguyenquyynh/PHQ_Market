package com.example.phq_market.model;

import java.io.Serializable;

public class ORDERANDFEEDBACK implements Serializable {
    private Integer ID;
    private String NAME;
    private Float PAY;
    private Integer QUANTITY;
    private String IMG;
    private Integer checkFeedBack;

    public ORDERANDFEEDBACK() {
    }

    public ORDERANDFEEDBACK(Integer ID, String NAME, Float PAY, Integer QUANTITY, String IMG, Integer checkFeedBack) {
        this.ID = ID;
        this.NAME = NAME;
        this.PAY = PAY;
        this.QUANTITY = QUANTITY;
        this.IMG = IMG;
        this.checkFeedBack = checkFeedBack;
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

    public Float getPAY() {
        return PAY;
    }

    public void setPAY(Float PAY) {
        this.PAY = PAY;
    }

    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public Integer getCheckFeedBack() {
        return checkFeedBack;
    }

    public void setCheckFeedBack(Integer checkFeedBack) {
        this.checkFeedBack = checkFeedBack;
    }
}
