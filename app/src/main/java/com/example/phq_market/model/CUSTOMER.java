package com.example.phq_market.model;

public class CUSTOMER {
    private String EMAIL;
    private String PASS;

    public CUSTOMER(String EMAIL, String PASS) {
        this.EMAIL = EMAIL;
        this.PASS = PASS;
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
}
