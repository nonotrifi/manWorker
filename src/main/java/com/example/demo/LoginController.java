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
import java.sql.Statement;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.Utils.showAlert;

public class LoginController{

    @FXML
    private TextField username, password;
    private Window owner;

    @FXML
    public void goToRegistration(ActionEvent e) throws IOException {
        Utils.loadPage("registration.fxml", e);
    }

    @FXML
    public void goToHome(ActionEvent e) throws IOException, SQLException {

        String message = isValidated(username.getText(), password.getText());

        if(Utils.isConfirm(message)){
            // Go to home
            Utils.loadPage("home.fxml", e);
            // Change the current user
            ManWorkerApplication.currentUser = username.getText();
        }
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    message);
        }
    }

    /*
        createStatement is when we want to create a command for the database. Could be a query (SELECT) or an update (INSERT, DEELETE ..)
     */
    public String isValidated(String username, String password) throws SQLException {
        String sql = "SELECT password FROM users WHERE name = ?;";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString (1, username);

        ResultSet result = preparedStmt.executeQuery();

        // CHecking if the output is empty
        if(!result.next()){
            return "Username is wrong";
        }
        String realPassword = result.getString("password");

        if (realPassword.compareTo(password) == 0) {
            return Utils.CONFIRM_MESSAGE;
        }
        else
            return "Password is wrong";

    }


}