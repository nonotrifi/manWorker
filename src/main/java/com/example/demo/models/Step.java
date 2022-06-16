package com.example.demo.models;

import java.util.ArrayList;

public class Step {
    private String name;
    private String description;

    public Step(String name, String description){
        this.name = name;
        this.description = description;

    }


    /*---------------------------------------- Getters and setters----------------------------------------- */
    public void setName(String newName){
        this.name = newName;
    }

    public String getName() {
        return name;
    }
}