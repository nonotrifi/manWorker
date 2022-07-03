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

        String message = isValidated(username.getText(), password.getText());

        if(message.compareTo("Confirm") == 0){
            // Go to home
            ManWorkerApplication.loadPage("home.fxml", e);
            // Change the current user
            ManWorkerApplication.currentUser = username.getText();
        }
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    message);
        }
    }

    public String isValidated(String username, String password) throws SQLException {
        Statement stmt = ManWorkerApplication.databaseLink.createStatement();

        String sql = "SELECT password FROM users WHERE name = '" + username +"';";

        ResultSet result = stmt.executeQuery(sql);

        if(!result.next()){
            return "Username is wrong";
        }
        else {
            System.out.println(result.getString(1));
            String realPassword = result.getString("password");

            if (realPassword.compareTo(password) == 0) {
                return "Confirm";
            }
            else
                return "Password is wrong";
        }
    }


}