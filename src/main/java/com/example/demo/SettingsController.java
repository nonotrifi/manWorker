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
    public void saveNewPassword(ActionEvent e) throws SQLException {
        Statement stmt = ManWorkerApplication.databaseLink.createStatement();

        String sql = "SELECT password FROM users WHERE name = ?;";

        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString(1, name.getText());

        ResultSet result = preparedStmt.executeQuery();

        if(result == null){
            ManWorkerApplication.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "An error occurred.");
            return;
        }

        result.next();

        String password = result.getString("password");

        if(result.wasNull()){
            ManWorkerApplication.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "The username is wrong.");
            name.requestFocus();
        }
        else if(oldPassword.getText().compareTo(password) != 0){
            ManWorkerApplication.showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "The old password is wrong.");
            oldPassword.requestFocus();
        }
        else if(newPassword.getText().length() < 5 || newPassword.getText().length() > 25){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Password text field cannot be less than 5 and greator than 25 characters.");
            newPassword.requestFocus();
        }
        else{
            sql = "UPDATE users SET password = ? WHERE name = ?;";

            preparedStmt = databaseLink.prepareStatement(sql);
            preparedStmt.setString(1, newPassword.getText());
            preparedStmt.setString(2, name.getText());
            preparedStmt.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation",
                    "Password changed correctly.");
        }
    }

}
