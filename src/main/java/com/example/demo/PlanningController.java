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

    // To not change the whole page and keep the menu
    @FXML
    private AnchorPane contentPlanning;

    /* Elements for creating a new planning */
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

    /* Table with the user's plannings */
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

    // When we go to an add steps page we need to say to this page what planning was clicked
    private AddStepsController addStepsController;
    Window owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Etape 1: Connecter les champs du tableau FXML aux attributs de la classe Planning

        // saying to java that the attribute name from the object planning will take place of the column name
        nameCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("description"));
        budgetCol.setCellValueFactory(new PropertyValueFactory<Planning, Float>("budget"));
        startCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("endDate"));
        teamCol.setCellValueFactory(new PropertyValueFactory<Planning, String>("team"));


        // Etape 2 : Récupérer les teamps présentes dans la base de données et les mettre dans le teamchoice côté front
        try {
            /* Setting up the teamChoice with the teams we have in the database */
            if(teamChoice != null){
                // We just want the teams that were cretaed by the user that is logged in
                String sql = "SELECT name FROM teams WHERE username = ?";

                PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
                preparedStmt.setString (1, ManWorkerApplication.currentUser);

                ResultSet result = preparedStmt.executeQuery();

                while(result.next()){
                    String teamName = result.getString("name");
                    teamChoice.getItems().add(teamName);
                }
            }


            // Etape 3 : Ajout des planning déjà présents dans la base de donnée côté FXML

            /* Adding the plannings created by the user that is logged in to the table */
            String sql = "SELECT * FROM plannings where username = ?";
            PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
            preparedStmt.setString (1, ManWorkerApplication.currentUser);
            ResultSet result = preparedStmt.executeQuery();
            Planning currentPlanning = null;

            /* result has the rows that are in the database, each row is used to create new planning objects
            and put them into the tableView in the interface
             */
            while(result.next()){
                /* For each result that we get from the database ( result is the object where we have all the rows we
                   create a new planning object, and we put it to the table
                */
                currentPlanning = new Planning(result.getInt("idPlanning"), result.getTimestamp("startDate"),
                        result.getTimestamp("endDate"), result.getString("name"),
                        result.getString("description"), new Team(result.getString("teamName")), result.getDouble("budget"));

                table.getItems().add(currentPlanning);
            }
        } catch (SQLException e) {
            // red error sql, printing the error
            e.printStackTrace();
        }


        // Etape 4 : Dire à Java qu'au moment ou on double clique sur l'un des planning on ouvrira le content steps

        /*
            This part is when we click the row we load a content. <>
            tv is a parameter of a lambda function - setRowFactory means an event handler to teach
         */
        table.setRowFactory( tv -> {
            // <> generics for example Array<String>, Planning is the object inside the tableRow
            // Pour chaque ligne dans la table Planning
            TableRow<Planning> planningRow = new TableRow<>();

            // Dans le cas ou le user clique sur l'une de ces lignes ->
            planningRow.setOnMouseClicked(event -> {
                // we have to say !row otherwise we can click everywhre and it shows error
                if (event.getClickCount() == 2 && (! planningRow.isEmpty()) ) {
                    FXMLLoader loader = Utils.loadContent("addSteps.fxml",contentPlanning);

                   // PlanningController needs to talk to addStepsController to let them know what planning was clicked
                    addStepsController = loader.getController();

                    try {
                        // Telling to addStepsController what planning was clicked, we are calling the setup from addstep
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
        // We convert in Java Date because before converting it was in DatePicker (javaFX)

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




        // Check if date1 is date 1 before date 2, we already converted Datepicker to Date Java with this line Date d1 = convert(startDate)
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

        // execute is when you press the bottom to execute a query
        preparedStmt.executeUpdate();

        // Car a chaque fois qu'on exécute la requête, Mysql autogénére une clé, la valeur de cette clé on la récupère avec rs.getInt()
        // Et on donne à idPlanning
        // GenerateKeys we use because is autoincremented from sql and we cannot know this without getGeneratedKey()
        ResultSet rs = preparedStmt.getGeneratedKeys();

        int idPlanning = 0;

        if (rs.next()) {
            idPlanning = rs.getInt(1);
        }

        // Creating new object (class Planning) and adding the object to fxml table
        // We have to create a new object to add to the table, those attributes take the values from fxml line 245
        Planning newPlanning = new Planning(idPlanning, startDate, endDate,
                name, description, new Team(teamName), budget);


        table.getItems().add(newPlanning);

    }

    @FXML
    private void deletePlanning() throws SQLException {
        // Deleting from the table
        Planning planning = (Planning)table.getSelectionModel().getSelectedItem();
        // Otherwise it tries to delete a non existing planning
        if(planning == null)
            return;

        table.getItems().remove(planning);

        // Deleting
        String sql = "DELETE FROM plannings WHERE idPlanning = ?";

        PreparedStatement pstmt = databaseLink.prepareStatement(sql);

        // set the corresponding param
        pstmt.setInt(1, planning.getIdPlanning());
        // execute the delete statement
        pstmt.executeUpdate();


        //ManWorkerApplication.plannings.remove(planning);
    }


}
