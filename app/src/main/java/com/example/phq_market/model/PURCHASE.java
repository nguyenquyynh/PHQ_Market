package com.example.phq_market.model;

import java.io.Serializable;

public class PURCHASE implements Serializable {
    private int IDCART;
    private String Email;
    private String Password;
    private int QUANTITY;

    public PURCHASE(int IDCART, String email, String password, int QUANTITY) {
        this.IDCART = IDCART;
        this.Email = email;
        this.Password = password;
        this.QUANTITY = QUANTITY;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public int getIDCART() {
        return IDCART;
    }

    public void setIDCART(int IDCART) {
        this.IDCART = IDCART;
    }


    public int getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(int QUANTITY) {
        this.QUANTITY = QUANTITY;
    }
}
