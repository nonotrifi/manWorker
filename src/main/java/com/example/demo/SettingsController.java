package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import static com.example.demo.ManWorkerApplication.currentUser;
import static com.example.demo.ManWorkerApplication.showAlert;

public class SettingsController{

    @FXML
    TextField name;

    @FXML
    TextField oldPassword;

    @FXML
    TextField newPassword;

    @FXML
    Window owner;
    @FXML
    public void saveNewPassword(ActionEvent e){
        if(name.getText().compareTo(ManWorkerApplication.currentUser.getUsername()) != 0){
            ManWorkerApplication.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "The username is wrong.");
            name.requestFocus();
        }
        else if(oldPassword.getText().compareTo(ManWorkerApplication.currentUser.getPassword()) != 0){
            ManWorkerApplication.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "The old password is wrong.");
            System.out.println(ManWorkerApplication.currentUser.getPassword());
            oldPassword.requestFocus();
        }
        else if(newPassword.getText().length() < 5 || newPassword.getText().length() > 25){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            newPassword.requestFocus();
        }
        else{
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Password changed correctly.");
            currentUser.setNewPassword(newPassword.getText());
        }
    }

}
