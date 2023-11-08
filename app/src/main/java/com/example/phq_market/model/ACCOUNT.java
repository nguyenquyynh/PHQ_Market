package com.example.phq_market.model;

public class ACCOUNT {
    private String NAME;
    private int PHONE;
    private String ADDRESS;
    private int LIKE;
    private int ORDER;
    private int ITEM;

    public ACCOUNT(String NAME, int PHONE, String ADDRESS, int LIKE, int ORDER, int ITEM) {
        this.NAME = NAME;
        this.PHONE = PHONE;
        this.ADDRESS = ADDRESS;
        this.LIKE = LIKE;
        this.ORDER = ORDER;
        this.ITEM = ITEM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getPHONE() {
        return PHONE;
    }

    public void setPHONE(int PHONE) {
        this.PHONE = PHONE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        ADDRESS = ADDRESS;
    }

    public int getLIKE() {
        return LIKE;
    }

    public void setLIKE(int LIKE) {
        this.LIKE = LIKE;
    }

    public int getORDER() {
        return ORDER;
    }

    public void setORDER(int ORDER) {
        this.ORDER = ORDER;
    }

    public int getITEM() {
        return ITEM;
    }

    public void setITEM(int ITEM) {
        this.ITEM = ITEM;
    }
}
