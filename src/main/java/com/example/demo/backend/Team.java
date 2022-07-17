package com.example.demo.backend;

public class Team{
    private String name;

    public Team(String name){
        this.name = String.valueOf(name);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

   @Override
    public String toString() {
       return this.name;
   }
}