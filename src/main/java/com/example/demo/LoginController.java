package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public static final int FRAME_HEIGHT = 800, FRAME_WIDTH = 1000;


    @FXML
    public void goToRegistration(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), FRAME_HEIGHT, FRAME_WIDTH);

        // https://stackoverflow.com/questions/32922424/how-to-get-the-current-opened-stage-in-javafx
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        System.out.println("Hello");
    }

    @FXML
    public void goToHome(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), FRAME_HEIGHT, FRAME_WIDTH);
        scene.getStylesheets().add(getClass().getResource("css/fullpackstyling.css").toExternalForm());
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}