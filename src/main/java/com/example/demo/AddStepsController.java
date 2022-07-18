package com.example.demo;

import com.example.demo.backend.Planning;
import com.example.demo.backend.Step;
import com.example.demo.exceptions.PlanningException;
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
import static com.example.demo.Utils.showAlert;

public class AddStepsController{
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

    public void setUp(Planning planning) throws PlanningException {
        if(planning == null) throw new PlanningException();


        /* Set current planning */
        this.planning = planning;

        planningNameSetUp();
        tableSetUp();
    }

    // title of the step of the planning
    public void planningNameSetUp(){
        planningName.setText("Add steps to: " + planning.getName());
    }


    public void tableSetUp(){
        //Defini les colonnes nameCol et description avec les valeurs et type necessaires
        nameCol.setCellValueFactory(new PropertyValueFactory<Step, String>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Step, String>("description"));

        String sql = "SELECT * FROM steps WHERE idPlanning = ?";

        try {
            PreparedStatement preparedStmt = databaseLink.prepareStatement(sql);
            preparedStmt.setInt (1, planning.getIdPlanning());

            ResultSet result = preparedStmt.executeQuery();

            /* result has the rows that are in the database, each row is used to create new planning objects
            and put them into the tableView in the interface
            the idStep is hidden but it's used in the back
             */

            //Recupere step pour les mettres dans la table 1a1
            while(result.next()){
                //ResulSet tourne en boucle car next retourne faux si rien apres
                table.getItems().add(new Step (result.getInt("idStep"),result.getString("name"), result.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStep() throws SQLException {
        String[] stepNameField = {"step name", name.getText()};
        String[] stepDescriptionField = {"step description", description.getText()};

        String stepNameMessage = Utils.checkField(stepNameField);
        String stepDescriptionMessage = Utils.checkField(stepDescriptionField);

        if(!Utils.isConfirm(stepNameMessage)){
            //Envoie Fenetre alerte erreur
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    stepNameMessage);
            //Remet le curseur sur name
            name.requestFocus();
            return;
        }

        if(!Utils.isConfirm(stepDescriptionMessage)){
            showAlert(Alert.AlertType.ERROR, owner, "Error",
                    stepDescriptionMessage);
            name.requestFocus();
            return;
        }


        insertNewStep();

    }

    //Pour l'insertion d'une new step, opn l'ajoute d'abord en base puis l'ajoute au tableau avec addStep
    public void insertNewStep() throws SQLException {
        String sql = "insert into steps(name, description, idPlanning)"
                + " values (?, ?, ?)";


        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = databaseLink.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString (1, name.getText());
        preparedStmt.setString   (2, description.getText());
        preparedStmt.setInt(3, planning.getIdPlanning());

        preparedStmt.executeUpdate();

        //Recupere l'id de la requete pour creer notre objet step
        ResultSet rs = preparedStmt.getGeneratedKeys();

        int idStep = 0;

        if (rs.next()) {
            idStep = rs.getInt(1);
        }

        Step newStep = new Step(idStep, name.getText(), description.getText() );

        planning.addStep(newStep);

        table.getItems().add(newStep);
    }

    @FXML
    private void deleteStep() throws SQLException {
        Step step = (Step)table.getSelectionModel().getSelectedItem();
        if(step == null)
            return;
        //On supprime de la table d'abord
        table.getItems().remove(step);
        //Puis en base
        String sql = "DELETE FROM steps WHERE idStep = ?";

        PreparedStatement pstmt = databaseLink.prepareStatement(sql);

        // set the corresponding param
        pstmt.setInt(1, step.getIdStep());
        // Execute the delete statement
        pstmt.executeUpdate();
    }
}
