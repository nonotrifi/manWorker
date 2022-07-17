package com.example.demo;

import com.example.demo.backend.Planning;
import com.example.demo.backend.Team;
import com.example.demo.exceptions.PlanningException;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

//import static com.example.demo.ManWorkerApplication.teams;
import static com.example.demo.ManWorkerApplication.currentUser;
import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.Utils.showAlert;

public class PlanningController implements Initializable {

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


    private AddStepsController addStepsController;
    Window owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        nameCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("description"));
        budgetCol.setCellValueFactory(new PropertyValueFactory<Planning, Float>("budget"));
        startCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("endDate"));
        teamCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("team"));



        try {

            if(teamChoice != null){

                String sql = "SELECT name FROM teams WHERE username = ?";

                PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
                preparedStmt.setString (1, ManWorkerApplication.currentUser);

                ResultSet result = preparedStmt.executeQuery();

                while(result.next()){
                    String teamName = result.getString("name");
                    teamChoice.getItems().add(teamName);
                }
            }



            String sql = "SELECT * FROM plannings where username = ?";
            PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
            preparedStmt.setString (1, ManWorkerApplication.currentUser);
            ResultSet result = preparedStmt.executeQuery();
            Planning currentPlanning = null;


            while(result.next()){

                currentPlanning = new Planning(result.getInt("idPlanning"), result.getTimestamp("startDate"),
                        result.getTimestamp("endDate"), result.getString("name"),
                        result.getString("description"), new Team(result.getString("teamName")), result.getDouble("budget"));

                table.getItems().add(currentPlanning);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }



        table.setRowFactory( tv -> {

            TableRow<Planning> planningRow = new TableRow<>();


            planningRow.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2 && (! planningRow.isEmpty()) ) {
                    FXMLLoader loader = Utils.loadContent("addSteps.fxml",contentPlanning);


                    addStepsController = loader.getController();

                    try {

                        addStepsController.setUp(planningRow.getItem());

                    } catch (PlanningException e) {
                        e.printStackTrace();
                    }
                }
            });
            return planningRow ;
        }
        );
    }

    @FXML
    public void addPlanning() throws SQLException {

        if(startDate.getValue() == null || endDate.getValue() == null){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Select a start and an end date.");
            return;
        }

        Date d1 = Utils.convertDate(startDate);
        Date d2 = Utils.convertDate(endDate);

        String[] planningNameField = {"name", name.getText()};
        String[] teamField = {"team", teamChoice.getValue()};
        String[] startDateField = {"start date", d1.toString()};
        String[] endDateField = {"end date", d2.toString()};


        String messageName = Utils.checkField(planningNameField);
        String messageTeam = Utils.checkIfBlank(teamField);
        String messageDateStart = Utils.checkIfBlank(startDateField);
        String messageDateEnd = Utils.checkIfBlank(endDateField);



        if (!Utils.isConfirm(messageName)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    messageName);
            return;
        }

        if (!Utils.isConfirm(messageDateStart)){
            if(startDate == null){
                showAlert(Alert.AlertType.ERROR,owner, "Error",
                        "startDate empty");
            }
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    messageDateStart);
            return;
        }

        if (!Utils.isConfirm(messageDateEnd)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    messageDateEnd);
            return;
        }

        if(!Utils.isNumeric(budget.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "Budget text field has to be numeric.");
            return;
        }

        if(!Utils.isConfirm(messageTeam)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    messageTeam);
            return;
        }





        if(d1.after(d2)){
            showAlert(Alert.AlertType.ERROR,owner, "Error",
                    "Start Date should be before End Date");
            return;
        }

        insertNewPlanning(name.getText(), description.getText(), Float.parseFloat(budget.getText()),
                new java.sql.Date(d1.getTime()), new java.sql.Date(d2.getTime()),
                teamChoice.getValue(), ManWorkerApplication.currentUser);

    }


    public void insertNewPlanning(String name, String description, float budget, java.sql.Date startDate, java.sql.Date endDate, String teamName, String username) throws SQLException {
        String sql = " insert into plannings(name, description, budget, startDate, endDate, teamName, username)"
                + " values (?, ?, ?, ?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        // Because we putting in the database that's autoincremented, if I don't put this it's gonna generate an error
        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString (1, name);
        preparedStmt.setString   (2, description);
        preparedStmt.setDouble(3, budget);
        preparedStmt.setDate(4, startDate);
        preparedStmt.setDate    (5, endDate);
        preparedStmt.setString    (6, teamName);
        preparedStmt.setString(7, username);


        preparedStmt.executeUpdate();


        ResultSet rs = preparedStmt.getGeneratedKeys();

        int idPlanning = 0;

        if (rs.next()) {
            idPlanning = rs.getInt(1);
        }


        Planning newPlanning = new Planning(idPlanning, startDate, endDate,
                name, description, new Team(teamName), budget);


        table.getItems().add(newPlanning);

    }

    @FXML
    private void deletePlanning() throws SQLException {
        Planning planning = (Planning)table.getSelectionModel().getSelectedItem();
        if(planning == null)
            return;

        table.getItems().remove(planning);

        String sql = "DELETE FROM plannings WHERE idPlanning = ?";

        PreparedStatement pstmt = databaseLink.prepareStatement(sql);
        pstmt.setInt(1, planning.getIdPlanning());
        pstmt.executeUpdate();



    }


}
