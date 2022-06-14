package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Team;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.PrefixSelectionChoiceBox;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.demo.HelloApplication.teams;
import static com.example.demo.RegistrationController.showAlert;

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


    Window owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(Team t: teams){
            teamChoice.getItems().add(t.getName());
        }

    }

    public static boolean isNumeric(String string) {
        int intValue;

        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    @FXML
    public void addPlanning(){
        if(!isNumeric(budget.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Budget text field has to be numeric.");
            return;
        }
        else if (name.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "name text field cannot be blank.");
            name.requestFocus();

        }

        else if(name.getText().length() < 2 || name.getText().length() >25 ){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "First name text field cannot be less than 2 and greator than 25 characters.");
            name.requestFocus();
        }

        else if(teamChoice.getItems().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "You has to select the team");
            return;
        }

        // other errors

        Planning newPlanning = new Planning(convertDate(startDate), convertDate(endDate),
                name.getText(), description.getText(), searchTeam(teamChoice.getValue()), Float.parseFloat(budget.getText()));
        HelloApplication.plannings.add(newPlanning);

        System.out.println(newPlanning.getTitle());
        System.out.println(newPlanning.getTeam().getName());
        System.out.println(newPlanning.getBudget());
        System.out.println(newPlanning.getStartDate());
    }

    private Date convertDate(DatePicker date){
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    private Team searchTeam(String teamName){
        for(Team t: teams){
            if(t.getName().compareTo(teamName) == 0)
                return t;
        }

        return null;
    }



//    private boolean validate(int text){
//        return text.matches("[0-9]*");
//    }
}
