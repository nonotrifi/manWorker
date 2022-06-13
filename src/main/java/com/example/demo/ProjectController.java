package com.example.demo;

import com.example.demo.models.Team;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.PrefixSelectionChoiceBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {

    @FXML
    private Button okButton;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private TextField budget;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private PrefixSelectionChoiceBox<String> teamChoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Team> teams = new ArrayList<>();

        teams.add(new Team("Team A"));
        teams.add(new Team("Team B"));
        teams.add(new Team("Team C"));

        for(Team t: teams){
            teamChoice.getItems().add(t.getName());
        }
    }


}
