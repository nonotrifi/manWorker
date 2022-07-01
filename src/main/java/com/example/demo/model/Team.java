package com.example.demo.model;

import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Team{
    private String name;

    public Team(String name){
        this.name = String.valueOf(name);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}