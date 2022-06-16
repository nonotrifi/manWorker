package com.example.demo.models;

import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Team{
    String name;
    ArrayList<User> admins;
    ArrayList<User> users;

    public Team(String name){
        this.name = String.valueOf(name);
        this.admins = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public boolean checkIfUserIsAdmin(User user){
        return this.admins.contains(user);
    }

    public void addAdmin(User user){
        this.admins.add(user);
    }

    public void addUser(User user){
        this.users.add(user);
    }
}