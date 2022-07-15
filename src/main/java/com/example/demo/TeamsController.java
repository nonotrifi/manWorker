package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.Utils.showAlert;


public class TeamsController{
    Window owner;

    @FXML
   private TextField name;

    @FXML
    public void addTeam() throws SQLException {
        String[] teamNameField = {"Team name", name.getText()};
        String teamNameMessage = Utils.checkField(teamNameField);

        if (!Utils.isConfirm(teamNameMessage)){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    teamNameMessage);
            name.requestFocus();
        }
        else if(teamNameAlreadyExists(name.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Team name already exists");
            name.requestFocus();
        }
        else
            insertNewTeam(name.getText());

    }

    public boolean teamNameAlreadyExists(String teamName) throws SQLException {
        String sql = "SELECT name FROM teams WHERE name = ?";
        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString (1, teamName);
        ResultSet result = preparedStmt.executeQuery();

        if(result.next()){
            return true;
        }
        else
            return false;
    }

    public void insertNewTeam(String teamName) throws SQLException{
        //ManWorkerApplication.teams.add(new Team(name.getText()));
        String sql = "INSERT INTO teams(name, username) VALUES (?, ?);";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString (1, teamName);
        preparedStmt.setString(2, ManWorkerApplication.currentUser);

        preparedStmt.execute();

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                "The team was added correctly.");
    }

}
