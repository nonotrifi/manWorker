package com.example.demo;

import java.io.IOException;

public interface PluginModel {
    default void initialize() throws IOException {
        System.out.println("Successfully initialized" + this.getClass().getName()) ;
    }
    default String name(){
        //return this.getClass().getSimpleName() ;
        return getClass().getSimpleName() ;
    }
}
