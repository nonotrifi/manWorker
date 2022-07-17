package com.example.demo;

public interface PluginModel {
    default void initialize(){
        System.out.println("Successfully initialized" + this.getClass().getName()) ;
    }
    default String name(){
        //return this.getClass().getSimpleName() ;
        return getClass().getSimpleName() ;
    }
}
