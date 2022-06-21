package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;

import java.io.IOException;
public class ConnectController {

    @FXML
    private Button connect;

    public Connection databaseLink;

    public Connection connectToMySQL(){
            String dbName = "mydb";
            String dbUsername = "root";
            String dbPassword = "admin";
            String url = "jdbc:mysql://localhost/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                databaseLink = DriverManager.getConnection(url,dbUsername, dbPassword);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return databaseLink;
        }


}
