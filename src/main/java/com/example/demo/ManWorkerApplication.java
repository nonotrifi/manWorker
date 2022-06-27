package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Step;
import com.example.demo.models.Team;
import com.example.demo.models.User;
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
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/*

TODOS

Home:
- fix aesthetic
- check if the end date is after the start date
- add team to database with insert
- test the app to find if there are bugs

- work with idTeam when adding a new planning
- i just want to see my plannings
*/

public class ManWorkerApplication extends Application {
    @FXML
    private Label showUsername;
    public static final int FRAME_HEIGHT = 800, FRAME_WIDTH = 1000;

    //static ArrayList<Planning> plannings = new ArrayList<Planning>();
    //static ArrayList<Team> teams = new ArrayList<>();

    static String currentUser;

    public static Connection databaseLink;

    @Override
    public void start(Stage stage) throws IOException {
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

        FXMLLoader fxmlLoader = new FXMLLoader(ManWorkerApplication.class.getResource("logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), FRAME_WIDTH, FRAME_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }


    public static void loadPage(String pageName, ActionEvent e) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(ManWorkerApplication.class.getResource(pageName));
        Scene scene = new Scene(fxmlLoader.load(), FRAME_WIDTH, FRAME_HEIGHT);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }


    public static void main(String[] args) {
        Step step = new Step("AAA", "BBB");
        launch();
    }
    
}