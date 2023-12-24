package com.example.phq_market.model;

public class CHECKOUT {
    private Integer IDCART;
    private Integer QUANTITY;


    public CHECKOUT(Integer IDCART, Integer QUANTITY) {
        this.IDCART = IDCART;
        this.QUANTITY = QUANTITY;
    }

    public Integer getIDCART() {
        return IDCART;
    }

    public void setIDCART(Integer IDCART) {
        this.IDCART = IDCART;
    }

    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }
}
