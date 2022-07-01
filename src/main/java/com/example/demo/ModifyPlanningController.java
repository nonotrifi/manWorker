package com.example.demo;

import com.example.demo.model.Planning;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ModifyPlanningController {
    private Planning planning;
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
    public void modifyPlanning(){
        planning.setName(name.getText());
    }

    public void setPlanning(Planning planning){
        this.planning = planning;
    }
}
