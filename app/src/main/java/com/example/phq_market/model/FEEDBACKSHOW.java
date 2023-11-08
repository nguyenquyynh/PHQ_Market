package com.example.phq_market.model;

public class FEEDBACKSHOW {
    private Integer ID;
    private Double EVALUATE;
    private String CONTENT;
    private String CUSTOMER;

    public FEEDBACKSHOW(Integer ID, Double EVALUATE, String CONTENT, String CUSTOMER) {
        this.ID = ID;
        this.EVALUATE = EVALUATE;
        this.CONTENT = CONTENT;
        this.CUSTOMER = CUSTOMER;
    }

    public FEEDBACKSHOW() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Double getEVALUATE() {
        return EVALUATE;
    }

    public void setEVALUATE(Double EVALUATE) {
        this.EVALUATE = EVALUATE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getCUSTOMER() {
        return CUSTOMER;
    }

    public void setCUSTOMER(String CUSTOMER) {
        this.CUSTOMER = CUSTOMER;
    }
}
