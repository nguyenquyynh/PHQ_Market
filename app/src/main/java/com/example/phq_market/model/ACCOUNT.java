package com.example.phq_market.model;

public class ACCOUNT {
    private String NAME;
    private String EMAIL;
    private String PHONE;
    private String ADDRESS;
    private int LIKED;
    private int PURCHASE;
    private int CART;

    public ACCOUNT(String NAME, String EMAIL, String PHONE, String ADDRESS, int LIKED, int PURCHASE, int CART) {
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.PHONE = PHONE;
        this.ADDRESS = ADDRESS;
        this.LIKED = LIKED;
        this.PURCHASE = PURCHASE;
        this.CART = CART;
    }

    public ACCOUNT() {
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

    public int getLIKED() {
        return LIKED;
    }

    public void setLIKED(int LIKED) {
        this.LIKED = LIKED;
    }

    public int getPURCHASE() {
        return PURCHASE;
    }

    public void setPURCHASE(int PURCHASE) {
        this.PURCHASE = PURCHASE;
    }

    public int getCART() {
        return CART;
    }

    public void setCART(int CART) {
        this.CART = CART;
    }
}
