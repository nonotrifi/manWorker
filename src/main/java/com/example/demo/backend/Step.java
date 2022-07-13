package com.example.demo.backend;

public class Step {
    private int idStep;
    private String name;
    private String description;


    public Step(int idStep, String name, String description){
        this.idStep = idStep;

        this.name = name;
        this.description = description;

    }

    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
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
   @Override
    public String toString(){
        return "Name: " + this.name + "\n" + "Description: " + this.description;
    }

}