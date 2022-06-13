package com.example.demo;


import com.example.demo.helper.AlertHelper;
import com.example.demo.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController  implements Initializable {
    public static User user;
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
    private Label errorTextPasswords;

    Window window;

    @FXML
    private Button registerButton;


    // !=
    @FXML
    public void goToHome(ActionEvent e) throws IOException {
        if (this.isValidated()) {
            if (password.getText().compareTo(password2.getText()) != 0) {
                errorTextPasswords.setVisible(true);
            } else
                user = new User(1, username.getText(), password.getText(), firstname.getText(), lastname.getText(), email.getText());
            System.out.println(user.getFirstName());
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), LoginController.FRAME_HEIGHT, LoginController.FRAME_WIDTH);
            scene.getStylesheets().add(getClass().getResource("css/fullpackstyling.css").toExternalForm());
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorTextPasswords.setVisible(false);
    }


    /*
    Means of firstname.requestForcus() :
     */
    @FXML
    private boolean isValidated() {
        Window owner = registerButton.getScene().getWindow();
        if (firstname.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
                    "First name text field cannot be blank.");
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
        } else {
            return true;
        }


        return false;
    }



    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}