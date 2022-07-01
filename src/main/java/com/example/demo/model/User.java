package com.example.demo.model;

import java.util.Date;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;


    public User(String username, String password, String firstName, String lastName, String email){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}