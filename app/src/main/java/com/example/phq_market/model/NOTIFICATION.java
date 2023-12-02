package com.example.phq_market.model;

public class NOTIFICATION {
    private int ID;
    private String TITLE;
    private String CONTENT;
    private int ROLE;
    private String DATE;

    public NOTIFICATION(int ID, String TITLE, String CONTENT, int ROLE, String DATE) {
        this.ID = ID;
        this.TITLE = TITLE;
        this.CONTENT = CONTENT;
        this.ROLE = ROLE;
        this.DATE = DATE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public int getROLE() {
        return ROLE;
    }

    public void setROLE(int ROLE) {
        this.ROLE = ROLE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
}
