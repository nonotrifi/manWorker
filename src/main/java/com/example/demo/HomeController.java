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
    private AnchorPane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AtomicBoolean flag = new AtomicBoolean(false);

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
        loadContent("plannings.fxml");
    }

    @FXML
    public void goToTeamsInterface(){
        loadContent("teams.fxml");
    }

    @FXML
    public void goToSettings(){
        loadContent("settings.fxml");
    }


    public void backToLogin(ActionEvent e) {
        this.backToLogin(e);
    }

    @FXML
    public void loadContent(String contentName){
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource(contentName));
        AnchorPane root;

        for(Object c: content.getChildren().toArray()){
            content.getChildren().remove(c);
        }

        try {
            root = loader.load();
            content.getChildren().add(root);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

    }

}