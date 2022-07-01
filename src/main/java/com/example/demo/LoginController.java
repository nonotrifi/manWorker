package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.ManWorkerApplication.showAlert;

public class LoginController{

    @FXML
    private TextField username, password;
    private Window owner;

    @FXML
    public void goToRegistration(ActionEvent e) throws IOException {
        ManWorkerApplication.loadPage("registration.fxml", e);
    }

    @FXML
    public void goToHome(ActionEvent e) throws IOException, SQLException {

        Statement stmt = ManWorkerApplication.databaseLink.createStatement();

        String sql = "SELECT password FROM users WHERE name = \'" + username.getText() +"\';";

        ResultSet result = stmt.executeQuery(sql);

        if(result == null)
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Username is worng.");
        else {
            result.first();
            String userPassword = result.getString("password");

            if (userPassword.compareTo(password.getText()) == 0) {
                ManWorkerApplication.loadPage("home.fxml", e);
                ManWorkerApplication.currentUser= username.getText();
            }
            else
                showAlert(Alert.AlertType.ERROR, owner, "Error",
                        "Password is wrong.");
        }

    }

}