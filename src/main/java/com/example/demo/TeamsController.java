package com.example.demo;

import com.example.demo.models.Team;
import javafx.fxml.FXML;
import com.example.demo.ProjectController;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class TeamsController  extends ProjectController {

    @FXML
   private TextField name;




    public void addTeam(){
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(new Team(name));


        System.out.println(name.getText());
    }

}
