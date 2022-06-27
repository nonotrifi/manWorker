package com.example.demo;

import com.example.demo.models.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.ManWorkerApplication.showAlert;


public class TeamsController{


    Window owner;

    @FXML
   private TextField name;

    @FXML
    public void addTeam(ActionEvent e) throws IOException, SQLException {
        if (name.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Name cannot be blank.");
            name.requestFocus();
        }
        else{
            //ManWorkerApplication.teams.add(new Team(name.getText()));

            String sql = "INSERT INTO teams(name) VALUES (?);";

            PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
            preparedStmt.setString (1, name.getText());

            preparedStmt.execute();

            showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "The team was added correctly.");
        }
    }

}
