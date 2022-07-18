package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class ManWorkerApplication extends Application {
    @FXML
    private Label showUsername;

    static String currentUser;

    public static Connection databaseLink;

    public static void connectToDatabase(){
        String dbName = "mydb";
        String dbUsername = "root";
        String dbPassword = "admin";
        String url = "jdbc:mysql://localhost/" + dbName
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,dbUsername, dbPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        connectToDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(ManWorkerApplication.class.getResource("logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Utils.FRAME_WIDTH, Utils.FRAME_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
    
}