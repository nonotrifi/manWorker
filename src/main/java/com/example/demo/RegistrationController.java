package com.example.demo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.Utils.showAlert;

public class RegistrationController{
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

    @FXML
    public void goToHome(ActionEvent e) throws IOException, SQLException {
        if (this.isValidated(username.getText(), firstname.getText(), lastname.getText(), email.getText(), password.getText(), password2.getText())) {
            registerUser(username.getText(), firstname.getText(), lastname.getText(), email.getText(), password.getText());
            Utils.loadPage("home.fxml", e);
        }
    }

    @FXML
    public void goToLogin(ActionEvent e) throws IOException{
        Utils.loadPage("logIn.fxml", e);
    }

    public void registerUser(String username, String firstName, String lastName, String email, String password) throws SQLException {
        String query = " insert into users(name, password, firstName, lastName, email)"
                + " values (?, ?, ?, ?, ?)";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(query);
        preparedStmt.setString (1, username);
        preparedStmt.setString   (2, password);
        preparedStmt.setString(3, firstName);
        preparedStmt.setString    (4, lastName);
        preparedStmt.setString    (5, email);

        preparedStmt.execute();
    }

    @FXML
    private boolean isValidated(String username, String firstname, String lastname, String email, String password, String password2) throws SQLException {
        Window owner = registerButton.getScene().getWindow();


        String message = checkRegistrationFields(username, firstname, lastname,
                email, password, password2);

        if(!Utils.isConfirm(message)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    message);
            return false;
        }

        if(usernameAlreadyExists(username)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "This username is already used");
            return false;
        }

        if (!areSamePasswords(password, password2)) {
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Passwords are not the same");
        }

        return true;
    }

    public String checkRegistrationFields(String username, String firstName, String lastName, String email, String password, String password2){

        String[][] fields = {{"username" , username } , {"firstname" , firstName }, {"lastname ", lastName },
                {"email", email }, {"password", password}, {"password", password2}};

        String message = Utils.checkMultipleFields(fields);

        if(message.compareTo(Utils.CONFIRM_MESSAGE) != 0)
            return message;

        return Utils.CONFIRM_MESSAGE;
    }

    public boolean usernameAlreadyExists(String username) throws SQLException {
        String sql = "SELECT name FROM users WHERE name = ?;";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString(1, username);

        ResultSet result = preparedStmt.executeQuery();


        if(result.next()){
            return true;
        }
        else
            return false;
    }


    public boolean areSamePasswords(String password, String password2){
        return password.compareTo(password2) == 0;
    }



}