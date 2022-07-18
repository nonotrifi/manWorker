package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.Utils.showAlert;

public class LoginController{

    @FXML
    private TextField username, password;
    private Window owner ;

    @FXML
    public void goToRegistration(ActionEvent e) throws IOException {
        Utils.loadPage("registration.fxml", e);
    }

    @FXML
    public void putPlugin(ActionEvent e ) throws Exception {
        if(PlugConnector.isLaunched){
            PlugConnector.close();
            return ;
        }
        PlugConnector.start() ;
        return ;
    }

    @FXML
    public void goToHome(ActionEvent e) throws IOException, SQLException {

        String message = isValidated(username.getText(), password.getText());

        if(Utils.isConfirm(message)){
            Utils.loadPage("home.fxml", e);
            ManWorkerApplication.currentUser = username.getText();
        }
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    message);
        }
    }

    public String isValidated(String username, String password) throws SQLException {
        String sql = "SELECT password FROM users WHERE name = ?;";
        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString (1, username);
        ResultSet result = preparedStmt.executeQuery();

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