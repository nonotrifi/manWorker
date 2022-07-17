package com.example.demo;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Window;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static com.example.demo.ManWorkerApplication.currentUser;
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

        String checkUsernameAndOldPasswordMessage = checkUsernameAndOldPassword(name.getText(), oldPassword.getText());

        if(name.getText().compareTo(currentUser) != 0){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "This is not the current user");
            return;
        }

        if(!Utils.isConfirm(checkUsernameAndOldPasswordMessage)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    checkUsernameAndOldPasswordMessage);
            return;
        }

        String newPasswordMessage = Utils.checkField(newPasswordField);

        if(!Utils.isConfirm(newPasswordMessage)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    newPasswordMessage);
            newPassword.requestFocus();
            return;
        }

        changePassword(newPassword.getText(), name.getText());
    }

    public String checkUsernameAndOldPassword(String username, String oldPassword) throws SQLException {
        String sql = "SELECT password FROM users WHERE name = ?;";
        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
        preparedStmt.setString(1, name.getText());
        ResultSet result = preparedStmt.executeQuery();
        if(!result.next()){
            System.out.println(result.next());
            return "The username you put is wrong.";
        }
        String databasePassword = result.getString("password");
        if(oldPassword.compareTo(databasePassword) != 0){
            return "The old password is wrong.";
        }


        return Utils.CONFIRM_MESSAGE;
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
