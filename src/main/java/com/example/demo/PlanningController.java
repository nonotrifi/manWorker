package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Team;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.Date;
import java.util.ResourceBundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import static com.example.demo.ManWorkerApplication.teams;
import static com.example.demo.ManWorkerApplication.databaseLink;
import static com.example.demo.ManWorkerApplication.showAlert;

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


    // set date


    Window owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(teamChoice != null){
            /*for(Team t: ManWorkerApplication.teams){
                teamChoice.getItems().add(t.getName());
            }*/

            Statement stmt = null;
            try {
                stmt = ManWorkerApplication.databaseLink.createStatement();
                String sql = "SELECT name FROM teams;";
                ResultSet result = stmt.executeQuery(sql);

                while(result.next())
                    teamChoice.getItems().add(result.getString("name"));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("description"));
        budgetCol.setCellValueFactory(new PropertyValueFactory<Planning, Float>("budget"));
        startCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("endDate"));
        teamCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("team"));

        /*for(Planning planning: ManWorkerApplication.plannings)
            table.getItems().add(planning);*/

        Statement stmt = null;
        try {
            stmt = ManWorkerApplication.databaseLink.createStatement();
            String sql = "SELECT * FROM plannings;";
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()){
                table.getItems().add(new Planning(result.getTimestamp("startDate"),
                                result.getTimestamp("endDate"), result.getString("name"),
                                result.getString("description"), new Team("Name"), result.getDouble("budget")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        table.setRowFactory( tv -> {
            TableRow<Planning> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    FXMLLoader loader = loadContent("addSteps.fxml");
                    addStepsController = loader.getController();
                    addStepsController.setPlanning(row.getItem());
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
    public void addPlanning(ActionEvent e) throws IOException, SQLException {
        Date d1 = convertDate(startDate);
        Date d2 = convertDate(endDate);
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

        else if(d1.after(d2)){
            showAlert(Alert.AlertType.ERROR,owner, "Error",
                    "Start Date should be before End Date");
            System.out.println("the type is " + ((Object)d1).getClass().getSimpleName());
        }
        else if(teamChoice.getItems().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    "You has to select the team");

            return;
        }
        else{
            Planning newPlanning = new Planning(convertDate(startDate), convertDate(endDate),
                    name.getText(), description.getText(), new Team(teamChoice.getValue()), Float.parseFloat(budget.getText()));

            table.getItems().add(newPlanning);

            String query = " insert into plannings(name, description, budget, startDate, endDate, idTeam)"
                    + " values (?, ?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = databaseLink.prepareStatement(query);
            preparedStmt.setString (1, newPlanning.getName());
            preparedStmt.setString   (2, newPlanning.getDescription());
            preparedStmt.setDouble(3, newPlanning.getBudget());
            preparedStmt.setDate(4, new java.sql.Date(newPlanning.getStartDate().getTime()));
            preparedStmt.setDate    (5, new java.sql.Date(newPlanning.getEndDate().getTime()));
            preparedStmt.setInt    (6, 1);

            preparedStmt.execute();
        }

    }

    @FXML
    private void deletePlanning(ActionEvent e){
        Planning planning = (Planning)table.getSelectionModel().getSelectedItem();
        table.getItems().remove(planning);
        //ManWorkerApplication.plannings.remove(planning);
    }


    private Date convertDate(DatePicker date){
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }


    @FXML
    public FXMLLoader loadContent(String contentName){
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource(contentName));

        for(Object c: contentPlanning.getChildren().toArray()){
            contentPlanning.getChildren().remove(c);
        }

        try {
            contentPlanning.getChildren().add(loader.load());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return loader;
    }



//    private boolean validate(int text){
//        return text.matches("[0-9]*");
//    }
}
