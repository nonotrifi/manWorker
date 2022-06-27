package com.example.demo.models;

import java.util.ArrayList;
import java.util.Date;

public class Planning {
    private String name;
    private String description; 
    private Date startDate;
    private Date endDate;
    private double budget;
    private ArrayList<Step> steps;
    private Team team;

    public Planning(Date stardDate, Date endDate, String title, String description, Team team, double budget){
        this.name = title;
        this.description = description;
        this.startDate = stardDate;
        this.endDate = endDate;
        this.budget = budget;
        this.team = team;
        this.steps = new ArrayList<Step>();
    }

    public void addStep(Step step){

        this.steps.add(step);
    }

    /*---------------------------------------- Getters and setters----------------------------------------- */

    public void setName(String newTitle){

        this.name = newTitle;
    }

    public String getName(){
        return this.name;
    }

    /* ------------------------------------- */

    public void setEndDate(Date newEndDate){
        this.endDate = newEndDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    /* ------------------------------------- */

    public void setStartDate(Date newstartdDate){
        this.startDate = newstartdDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    /* ------------------------------------- */

    public void setBudget (float newBudget){
        this.budget = newBudget;
    }

    public double getBudget(){
        return this.budget;
    }

    /* ------------------------------------- */

    public void setDescription (String newDescription){
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Step> getSteps() {return this.steps;}

    /* ------------------------------------- */

    public Team getTeam(){
        return this.team;
    }

    public void modifyStepName(int indexStep, String newName){
        this.steps.get(indexStep).setName(newName);
    }


}