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
import java.sql.Statement;

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


    // !=
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

    /*
        preparedStmt is placing what is entering from the fxml file
     */
    public void registerUser(String username, String firstName, String lastName, String email, String password) throws SQLException {
        String query = " insert into users(name, password, firstName, lastName, email)"
                + " values (?, ?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = databaseLink.prepareStatement(query);
        preparedStmt.setString (1, username);
        preparedStmt.setString   (2, password);
        preparedStmt.setString(3, firstName);
        preparedStmt.setString    (4, lastName);
        preparedStmt.setString    (5, email);

        preparedStmt.execute();
    }


    /*
    Means of firstname.requestForcus() : surligner l'erreur en gras
     */
    @FXML
    private boolean isValidated(String username, String firstname, String lastname, String email, String password, String password2) throws SQLException {
        Window owner = registerButton.getScene().getWindow();

        /* Check if any of the fields are blank or the length is not right */

        String message = checkRegistrationFields(username, firstname, lastname,
                email, password, password2);

        if(!Utils.isConfirm(message)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    message);
            return false;
        }

        /* If we find another user with the same username from the database,
        it means that the username is already used
         */
        if(usernameAlreadyExists(username)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "This username is already used");
            return false;
        }

        /* Check if the repeated password is equal to the first password */

        if (!areSamePasswords(password, password2)) {
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Passwords are not the same");
        }

        return true;
    }

    /* Checking if a string is blank "    " or if the length is not in the range 3-15 */
    public String checkRegistrationFields(String username, String firstName, String lastName, String email, String password, String password2){
        /* First element: text we need to create the error message ( for ex. " firstname cannot be blank" ),
        second element: the value ( for ex. "nono" )
         */
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

        // executeQuery is the bottom that we press in workbrench to execute
        ResultSet result = preparedStmt.executeQuery();

        /* If we find another user with the same username from the database,
        it means that the username is already used
         */
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