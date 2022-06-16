package com.example.demo;

import com.example.demo.models.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.example.demo.ManWorkerApplication.showAlert;


public class TeamsController  extends ProjectController {

    @FXML
   private TextField name;

    @FXML
    public void addTeam(ActionEvent e) throws IOException  {
        if (name.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Name cannot be blank.");
            name.requestFocus();
        }
        else{
            ManWorkerApplication.teams.add(new Team(name.getText()));
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "The team was added correctly.");
        }
    }

}
