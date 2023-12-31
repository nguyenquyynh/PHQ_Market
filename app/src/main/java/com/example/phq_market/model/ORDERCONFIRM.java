package com.example.phq_market.model;

import java.util.ArrayList;

public class ORDERCONFIRM {
    private Integer ID;
    private String CODEORDER;
    private Integer PAYMENT;
    private Float PAY;
    private String DATE;
    private ArrayList<ORDERANDFEEDBACK> listdetail;

    public ORDERCONFIRM(Integer ID, String CODEORDER, Integer PAYMENT, Float PAY, String DATE, ArrayList<ORDERANDFEEDBACK> listdetail) {
        this.ID = ID;
        this.CODEORDER = CODEORDER;
        this.PAYMENT = PAYMENT;
        this.PAY = PAY;
        this.DATE = DATE;
        this.listdetail = listdetail;
    }

    public ArrayList<ORDERANDFEEDBACK> getListdetail() {
        return listdetail;
    }

    public void setListdetail(ArrayList<ORDERANDFEEDBACK> listdetail) {
        this.listdetail = listdetail;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCODEORDER() {
        return CODEORDER;
    }

    public void setCODEORDER(String CODEORDER) {
        this.CODEORDER = CODEORDER;
    }

    public Integer getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(Integer PAYMENT) {
        this.PAYMENT = PAYMENT;
    }

    public Float getPAY() {
        return PAY;
    }

    public void setPAY(Float PAY) {
        this.PAY = PAY;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

}
