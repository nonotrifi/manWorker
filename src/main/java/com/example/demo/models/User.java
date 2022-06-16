package com.example.demo.models;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    

    public User(int id, String username, String password, String firstName, String lastName, String email){
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    /*
     * 
     */

    public Planning createPlanning(Date startDate, Date endDate, String title, String description, Team team, int budget){
        if(!team.checkIfUserIsAdmin(this)){
            return null;
        }
        Planning newPlanning = new Planning(startDate, endDate, title, description, team, budget);
        return newPlanning;
    }

    public void addStep(Planning planning, Step step){
        if(!planning.getTeam().checkIfUserIsAdmin(this)){
            return;
        }

        planning.addStep(step);
    }

    public void modifyPlanningTitle(Planning planning, String newTitle){
        if(!planning.getTeam().checkIfUserIsAdmin(this)){
            return;
        }

        planning.setName(newTitle);
    }

    // modifyPlanningBudget
    public void modifyPlanningBudget(Planning planning, int newBudget){
        if(!planning.getTeam().checkIfUserIsAdmin(this)){
            return;
        }
        planning.setBudget(newBudget);
    }

    // modifyPlanningEndDate
    public void modifyPlanningEndDate(Planning planning, Date newendDate){
        if(!planning.getTeam().checkIfUserIsAdmin(this)){
            return;
        }
        planning.setEndDate(newendDate);
    }
    // modifyPlanningDescription
    public void modifyPlanningDescription(Planning planning, String newDescription){
        if(!planning.getTeam().checkIfUserIsAdmin(this)){
            return;
        }
        planning.setDescription(newDescription);
    }

    // modifyStepName(Planning planning, int indexStep, String newName)

    public void modifyStepName(Planning planning, int indexStep, String newName){
        if(!planning.getTeam().checkIfUserIsAdmin(this)){
            return;
        }

        planning.modifyStepName(indexStep, newName);

    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setNewPassword(String newPassword){
        this.password = newPassword;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }

    // superAdmin celui qui va créer les autres admins
}