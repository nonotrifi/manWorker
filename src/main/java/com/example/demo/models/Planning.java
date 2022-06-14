package com.example.demo.models;

import java.util.ArrayList;
import java.util.Date;

public class Planning {
    private String title;
    private String description; 
    private Date startDate;
    private Date endDate;
    private float budget;
    private ArrayList<Step> steps;
    private Team team;

    public Planning(Date stardDate, Date endDate, String title, String description, Team team, float budget){
        this.title = title;
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

    public void addTask(int indexStep, Task task){

        this.steps.get(indexStep).getTasks().add(task);
    }

    /*---------------------------------------- Getters and setters----------------------------------------- */

    public void setTitle(String newTitle){

        this.title = newTitle;
    }

    public String getTitle(){
        return this.title;
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

    public float getBudget(){
        return this.budget;
    }

    /* ------------------------------------- */

    public void setDescription (String newDescription){
        this.description = newDescription;
    }

    public String getDescription() {
        return description;
    }

    /* ------------------------------------- */

    public Team getTeam(){
        return this.team;
    }

    public void modifyStepName(int indexStep, String newName){
        this.steps.get(indexStep).setName(newName);
    }

    public void deleteTask(int indexStep, int indexTask){
        this.steps.get(indexStep).getTasks().remove(indexTask);
    }
}