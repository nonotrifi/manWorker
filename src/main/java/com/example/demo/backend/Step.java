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
    @Override
    public String toString() {
        return "Step " + name +
                "\n" +
                "Step Id : " + idStep +
                "\n" +
                "Step Title --> " + name +
                "\n" +
                "Step description : " + description +
                "\n" +
                "\n";
    }


    /*---------------------------------------- Getters and setters----------------------------------------- */
    public void setName(String newName){
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public void setId(int idStep){
        this.idStep = idStep;
    }

    public int getIdStep(){
        return idStep;
    }

    public String getDescription(){
        return this.description;
    }

//   @Override
//    public String toString(){
//        return "Name: " + this.name + "\n" + "Description: " + this.description;
//    }
}