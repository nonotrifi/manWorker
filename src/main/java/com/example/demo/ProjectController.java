package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import org.controlsfx.control.PrefixSelectionChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.demo.ManWorkerApplication.teams;
import static com.example.demo.ManWorkerApplication.showAlert;

public class ProjectController implements Initializable {

    @FXML
    private AnchorPane contentPlanning;

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

    @FXML
    private TableView table;

    @FXML
    private TableColumn<Planning, String> nameCol;
    @FXML
    private TableColumn<Planning, String> descriptionCol;
    @FXML
    private TableColumn<Planning, Float> budgetCol;
    @FXML
    private TableColumn<Planning, String> startCol;
    @FXML
    private TableColumn<Planning, String> endCol;
    @FXML
    private TableColumn<Planning, String> teamCol;


    Window owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(teamChoice != null){
            for(Team t: ManWorkerApplication.teams){
                teamChoice.getItems().add(t.getName());
            }
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("description"));
        budgetCol.setCellValueFactory(new PropertyValueFactory<Planning, Float>("budget"));
        startCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("endDate"));
        teamCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("team"));

        for(Planning planning: ManWorkerApplication.plannings)
            table.getItems().add(planning);

        table.setRowFactory( tv -> {
            TableRow<Planning> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Planning rowData = row.getItem();
                    loadContent("addSteps.fxml");
                }
            });
            return row ;
        });
    }

    public static boolean isNumeric(String string) {
        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    @FXML
    public void addPlanning(ActionEvent e) throws IOException {
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
        else{
            Planning newPlanning = new Planning(convertDate(startDate), convertDate(endDate),
                    name.getText(), description.getText(), searchTeam(teamChoice.getValue()), Float.parseFloat(budget.getText()));
            ManWorkerApplication.plannings.add(newPlanning);

            table.getItems().add(newPlanning);
        }

    }

    @FXML
    private void deletePlanning(ActionEvent e){
        Planning planning = (Planning)table.getSelectionModel().getSelectedItem();
        table.getItems().remove(planning);
        ManWorkerApplication.plannings.remove(planning);
    }

    @FXML
    private void modifyPlanning(){

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


    @FXML
    public void loadContent(String contentName){
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource(contentName));
        AnchorPane root;

        for(Object c: contentPlanning.getChildren().toArray()){
            contentPlanning.getChildren().remove(c);
        }

        try {
            root = loader.load();
            contentPlanning.getChildren().add(root);
        } catch (IOException ioe) {
            return;
        }

    }



//    private boolean validate(int text){
//        return text.matches("[0-9]*");
//    }
}
