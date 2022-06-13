package com.example.demo.models;

import java.util.ArrayList;

public class Step {
    private ArrayList<Task> tasks;
    private String name;
    private String description;

    public Step(String name, String description){
        this.tasks = new ArrayList<Task>();
        this.name = name;
        this.description = description;

    }
    
    public void addTask(Task task){

        this.tasks.add(task);
    }


    /*---------------------------------------- Getters and setters----------------------------------------- */
    public void setName(String newName){
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasks(){
        return this.tasks;
    }
}