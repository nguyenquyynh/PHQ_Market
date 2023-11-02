package com.example.phq_market.model;

public class PRODUCT {
    private Integer ID;
    private String NAME;
    private String DESCRIBE;
    private Integer QUANTITY;
    private Float PRICE;
    private Integer IDCATALOG;

    public PRODUCT() {
    }

    public PRODUCT(Integer ID, String NAME, String DESCRIBE, Integer QUANTITY, Float PRICE, Integer IDCATALOG) {
        this.ID = ID;
        this.NAME = NAME;
        this.DESCRIBE = DESCRIBE;
        this.QUANTITY = QUANTITY;
        this.PRICE = PRICE;
        this.IDCATALOG = IDCATALOG;
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

    public String getDESCRIBE() {
        return DESCRIBE;
    }

    public void setDESCRIBE(String DESCRIBE) {
        this.DESCRIBE = DESCRIBE;
    }

    public Integer getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(Integer QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public Float getPRICE() {
        return PRICE;
    }

    public void setPRICE(Float PRICE) {
        this.PRICE = PRICE;
    }

    public Integer getIDCATALOG() {
        return IDCATALOG;
    }

    public void setIDCATALOG(Integer IDCATALOG) {
        this.IDCATALOG = IDCATALOG;
    }
}
