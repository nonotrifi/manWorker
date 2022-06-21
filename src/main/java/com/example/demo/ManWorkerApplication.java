package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Step;
import com.example.demo.models.Team;
import com.example.demo.models.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;

/*

TODOS

Home:
- add possibility modify plannings ( change name, description, budget....)
- fix aesthetic

Tomorrow:
- Create a database Mysql
- do the database

Later:
- page to add people to your teams

*/

public class ManWorkerApplication extends Application {
    public static final int FRAME_HEIGHT = 800, FRAME_WIDTH = 1000;

    static ArrayList<Planning> plannings = new ArrayList<Planning>();
    static ArrayList<Team> teams = new ArrayList<>();

    static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        teams.add(new Team("A"));
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