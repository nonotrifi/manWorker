package com.example.demo.exceptions;

public class PlanningException extends Exception{
    public PlanningException (){
        super() ;
    }
    public PlanningException (String errMsg){
        super(errMsg) ;
    }
}
