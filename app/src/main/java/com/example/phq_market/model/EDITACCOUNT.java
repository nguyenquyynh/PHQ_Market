package com.example.phq_market.model;

import java.io.Serializable;

public class EDITACCOUNT implements Serializable {
    private String NAME;
    private String EMAIL;
    private String PASS;
    private String PHONE;
    private String ADDRESS;
    private String IMG;

    public EDITACCOUNT(String NAME, String EMAIL, String PASS, String PHONE, String ADDRESS, String IMG) {
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.PASS = PASS;
        this.PHONE = PHONE;
        this.ADDRESS = ADDRESS;
        this.IMG = IMG;
    }

    public EDITACCOUNT() {
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }
}
