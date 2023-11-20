package com.example.phq_market.select_adress;

import java.util.ArrayList;

public class Districts {
    private int Id;
    private String Name;
    private ArrayList<Wards> Wards;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Wards> getWards() {
        return Wards;
    }

    public void setWards(ArrayList<Wards> wards) {
        Wards = wards;
    }
}
