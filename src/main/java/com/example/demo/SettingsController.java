package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;

//import static com.example.demo.ManWorkerApplication.currentUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.Utils.showAlert;

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
    public void saveNewPassword() throws SQLException {
        String[] newPasswordField = {"new password", newPassword.getText()};

        // ? means something you want to replace with prepared Statement;
        String sql = "SELECT password FROM users WHERE name = ?;";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString(1, name.getText());

        ResultSet result = preparedStmt.executeQuery();

        if(!result.next()){
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "The username you put is wrong.");
            return;
        }

        String password = result.getString("password");
        String newPasswordMessage = Utils.checkField(newPasswordField);

        if(oldPassword.getText().compareTo(password) != 0){
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "The old password is wrong.");
            oldPassword.requestFocus();
            return;
        }

        if(!Utils.isConfirm(newPasswordMessage)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    newPasswordMessage);
            newPassword.requestFocus();
            return;
        }

        changePassword(newPassword.getText(), name.getText());
    }

    public void changePassword(String newPassword, String username) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE name = ?;";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString(1, newPassword);
        preparedStmt.setString(2, username);
        preparedStmt.executeUpdate();

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                "Password changed correctly.");
    }

}
