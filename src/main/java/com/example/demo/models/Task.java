package com.example.demo.models;

import java.util.Date;

public class Task {
    private String title;
    private String description;
    private Team team;
    private Date startDate;
    private Date endDate;


    /*
     * ArrayList is like an array, but if you want to add something you don't need to worry about the indexes.
     * <> You specify the type of the element inside, and I have to library java.util
     */
    public Task(String title, String description, Date startDate, Date endDate, Team team){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Team getTeam(){
        return this.team;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    //public void connectedDoc();
}