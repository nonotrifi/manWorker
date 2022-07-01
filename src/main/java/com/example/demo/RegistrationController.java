package com.example.demo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.ManWorkerApplication.showAlert;

public class RegistrationController  implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField password;
    @FXML
    private TextField password2;
    @FXML
    private TextField email;

    @FXML
    private Button registerButton;


    // !=
    @FXML
    public void goToHome(ActionEvent e) throws IOException, SQLException {
        if (this.isValidated()) {
            String query = " insert into users(name, password, firstName, lastName, email)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = databaseLink.prepareStatement(query);
            preparedStmt.setString (1, username.getText());
            preparedStmt.setString   (2, password.getText());
            preparedStmt.setString(3, firstname.getText());
            preparedStmt.setString    (4, lastname.getText());
            preparedStmt.setString    (5, email.getText());

            preparedStmt.execute();

            ManWorkerApplication.loadPage("home.fxml", e);
        }
    }

    @FXML
    public void goToLogin(ActionEvent e) throws IOException{
        ManWorkerApplication.loadPage("logIn.fxml", e);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    /*
    Means of firstname.requestForcus() : surligner l'erreur en gras
     */
    @FXML
    private boolean isValidated() throws SQLException {
        Window owner = registerButton.getScene().getWindow();

        Statement stmt = ManWorkerApplication.databaseLink.createStatement();

        String sql = "SELECT name FROM users WHERE name = \'" + username.getText() +"\';";

        ResultSet result = stmt.executeQuery(sql);

        if(result.next()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "this username is already used.");
            username.requestFocus();
            return false;
        }


        if (firstname.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "firstname text field cannot be blank.");
            firstname.requestFocus();

        } else if(firstname.getText().length() < 2 || firstname.getText().length() >25 ){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "First name text field cannot be less than 2 and greator than 25 characters.");
            firstname.requestFocus();
        }
        else if (lastname.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Last name text field cannot be blank.");
            lastname.requestFocus();

        } else if(lastname.getText().length() < 2 || lastname.getText().length() > 25){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Last name text field cannot be less than 2 and greator than 25 characters.");
            lastname.requestFocus();
        } else if (email.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Email cannot be blank.");
            email.requestFocus();
        } else if(email.getText().length() < 5 || email.getText().length() > 45){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Email text field cannot be less than 5 and greator than 45 characters.");
            email.requestFocus();
        } else if(username.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "username text field cannot be less than 5 and greator than 25 characters.");
            username.requestFocus();
        } else if(password.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Password text field cannot be blank.");
            password.requestFocus();
        } else if(password.getText().length() < 5 || password.getText().length() > 25){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            password.requestFocus();
        } else if(password2.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Confirm password text field cannot be blank.");
            password2.requestFocus();
        } else if(password2.getText().length() < 5 || password2.getText().length() > 25){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Confirm password text field cannot be less than 5 and greator than 25 characters.");
            password2.requestFocus();
        } else if (password.getText().compareTo(password2.getText()) != 0) {
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Passwords are not the same.");
            password2.requestFocus();
        }
        else {
            return true;
        }


        return false;
    }


}