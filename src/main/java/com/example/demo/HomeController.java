package com.example.demo;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeController implements Initializable {

    @FXML
    private Label Menu;

    @FXML
    private AnchorPane slider;

    @FXML
    private AnchorPane homeContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(location);
        //System.out.println(resources);
        AtomicBoolean flag = new AtomicBoolean(true);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));
        slide.setNode(slider);

        slide.setToX(0);

        Menu.setOnMouseClicked(event -> {

            if(!flag.get()){
                slide.setToX(-176);
                slide.play();
                slider.setTranslateX(0);

                slide.setOnFinished((ActionEvent e)-> {
                    flag.set(true);
                });
            }
            else {
                slide.setToX(0);
                slide.play();
                slider.setTranslateX(-176);

                slide.setOnFinished((ActionEvent e)-> {
                    flag.set(false);
                });
            }

        });



    }

    @FXML
    public void goToNewProject(){
        Utils.loadContent("plannings.fxml", homeContent);
    }

    @FXML
    public void goToTeamsInterface(){
        Utils.loadContent("teams.fxml", homeContent);
    }

    /*@FXML
    public void goToConnectionDb(){
        Utils.loadContent("connectToDatabase.fxml", homeContent);
    }*/

    @FXML
    public void goToSettings(){
        Utils.loadContent("settings.fxml", homeContent);
    }


    @FXML
    public void backToLogin(ActionEvent e) throws IOException {
        Utils.loadPage("logIn.fxml", e);
    }

}