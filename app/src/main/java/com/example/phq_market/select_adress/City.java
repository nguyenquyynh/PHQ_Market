package com.example.phq_market.select_adress;

import java.util.ArrayList;

public class City {
    private int Id;
    private String Name;
    private ArrayList<Districts> Districts;

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

    public ArrayList<Districts> getDistricts() {
        return Districts;
    }

    public void setDistricts(ArrayList<Districts> districts) {
        Districts = districts;
    }
}
