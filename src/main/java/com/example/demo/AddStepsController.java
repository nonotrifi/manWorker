package com.example.demo;

import com.example.demo.backend.Planning;
import com.example.demo.backend.Step;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.ManWorkerApplication.showAlert;

public class AddStepsController implements Initializable{

    private Planning planning;
    @FXML
    private Label planningName;

    private int planningId;

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
        nameCol.setCellValueFactory(new PropertyValueFactory<Step, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Step, String>("description"));

        this.planning = planning;
        if(planning != null)
            planningName.setText("Add steps to: " + planning.getName());

        this.planningId = planning.getIdPlanning();

        for(Step step: planning.getSteps())
            table.getItems().add(step);


        String sql = "SELECT * FROM steps WHERE idPlanning = ?";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = databaseLink.prepareStatement(sql);
            preparedStmt.setInt (1, planningId);

            ResultSet result = preparedStmt.executeQuery();

            /* result has the rows that are in the database, each row is used to create new planning objects
            and put them into the tableView in the interface
             */

            while(result.next()){
                System.out.println(result.getString("description"));
                table.getItems().add(new Step (result.getInt("idStep"),result.getString("name"), result.getString("description")
                       ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void insertStep(ActionEvent e) throws IOException, SQLException {
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

            String query = "insert into steps(name, description, idPlanning)"
                    + " values (?, ?, ?)";


            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = databaseLink.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setString (1, name.getText());
            preparedStmt.setString   (2, description.getText());
            preparedStmt.setInt(3, planningId);

            System.out.println(planningId);

            preparedStmt.executeUpdate();

            ResultSet rs = preparedStmt.getGeneratedKeys();

            int idStep = 0;

            if (rs.next()) {
                idStep = rs.getInt(1);
            }

            Step newStep = new Step(idStep, name.getText(), description.getText() );

            planning.addStep(newStep);

            table.getItems().add(newStep);
        }

    }

    @FXML
    private void deleteStep(ActionEvent e) throws SQLException {
        Step step = (Step)table.getSelectionModel().getSelectedItem();
        table.getItems().remove(step);

        String sql = "DELETE FROM steps WHERE idStep = ?";

        PreparedStatement pstmt = databaseLink.prepareStatement(sql);

        // set the corresponding param
        pstmt.setInt(1, step.getIdStep());
        // execute the delete statement
        pstmt.executeUpdate();
    }
}
