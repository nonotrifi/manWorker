package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Step;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.demo.ManWorkerApplication.showAlert;

public class AddStepsController implements Initializable{

    private Planning planning;
    @FXML
    private Label planningName;

    @FXML
    private TableColumn<Step, String> nameCol;
    @FXML
    private TableColumn<Step, String> descriptionCol;

    @FXML
    private TextField name;

    @FXML
    private TextArea description;

    @FXML
    private TableView table;

    Window owner;

    public void setPlanning(Planning planning){
        this.planning = planning;
        if(planning != null)
            planningName.setText("Add steps to: " + planning.getName());

        for(Step step: planning.getSteps())
            table.getItems().add(step);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<Step, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Step, String>("description"));
    }

    @FXML
    public void insertStep(ActionEvent e) throws IOException {
        if (name.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "name text field cannot be blank.");
            name.requestFocus();

        }

        else if(name.getText().length() < 2 || name.getText().length() >25 ){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "First name text field cannot be less than 2 and greator than 25 characters.");
            name.requestFocus();
        }
        else{
            Step newStep = new Step(name.getText(), description.getText());
            planning.addStep(newStep);

            table.getItems().add(newStep);
        }

    }

    @FXML
    private void deleteStep(ActionEvent e){
        Step step = (Step)table.getSelectionModel().getSelectedItem();
        table.getItems().remove(step);
        planning.getSteps().remove(step);
    }
}
