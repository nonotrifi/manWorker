package com.example.demo.backend;

import java.util.ArrayList;
import java.util.Date;

public class Planning {
    private int idPlanning;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private double budget;
    private ArrayList<Step> steps;
    private Team team;

    public Planning(int idPlanning, Date startDate, Date endDate, String title, String description, Team team, double budget){
        this.idPlanning = idPlanning;
        this.name = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.team = team;
        this.steps = new ArrayList<Step>();
    }

    public void addStep(Step step){

        this.steps.add(step);
    }

    public int getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(int idPlanning) {
        this.idPlanning = idPlanning;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }


//    /*---------------------------------------- Getters and setters----------------------------------------- */
//
//    public void setName(String newTitle){
//
//        this.name = newTitle;
//    }
//
//    public String getName(){
//        return this.name;
//    }
//
//    /* ------------------------------------- */
//
//    public Date getEndDate() {
//        return endDate;
//    }
//
//    /* ------------------------------------- */
//
//    public Date getStartDate() {
//        return startDate;
//    }
//
//    /* ------------------------------------- */
//
//    public double getBudget(){
//        return this.budget;
//    }
//
//    /* ------------------------------------- */
//
//    public String getDescription() {
//        return description;
//    }
//
//    public ArrayList<Step> getSteps() {return this.steps;}
//
//    /* ------------------------------------- */
//
//    public Team getTeam(){
//        return this.team;
//    }
//
//    /* ------------------------------------- */
//
//    public int getIdPlanning() {
//        return idPlanning;
//    }
//
//    public void setIdPlanning(int idPlanning){
//        this.idPlanning = idPlanning;
//    }


}