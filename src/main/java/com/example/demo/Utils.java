package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static final int FRAME_HEIGHT = 800, FRAME_WIDTH = 1000;
    public static final String CONFIRM_MESSAGE = "Confirm";

    /* This function gets a value for a field ( for example " Alice " for " name " ) and if the value is blank ( for example
     * field = "" it would return a message saying the text field name ( which is " name " in the example ) and returns
     * " name field cannot be blank "
     *
     * If we don't put static I have to create a utils object to use the method
     * */
    //Controles sur champ et retourne un msg d'erreur ou bien "confirm"
    public static String checkIfBlank(String[] field){
        if(field == null)
            return "An error occurred";


        String fieldName = field[0], fieldValue = field[1];

        if(fieldName == null)
            return "An error occurred";

        if(fieldValue == null)
            return fieldName + " field cannot be blank";

        if (fieldValue.isBlank()) {
            return fieldName + " field cannot be blank";
        }

        return CONFIRM_MESSAGE;
    }

    //Check la taille d'un champ pour limiter a 2chars minimum et max 25
    public static String checkLength(String[] field){
        if(field == null)
            return "An error occurred";

        String fieldName = field[0], fieldValue = field[1];

        if(fieldName == null)
            return "An error occurred";

        if(fieldValue == null)
            return fieldName + " field cannot be blank";

        if(fieldValue.length() < 2 || fieldValue.length() > 25){
            return fieldName + " cannot be less than 5 and greater than 25 characters.";
        }

        return CONFIRM_MESSAGE;
    }

    // We created this for name in the planning
    public static String checkField(String[] field){
        String blankMessage, lengthMessage;

        // Grace a la method static dans utils
        blankMessage = Utils.checkIfBlank(field);
        lengthMessage = Utils.checkLength(field);

        if(!isConfirm(blankMessage))
            return blankMessage;

        if(!isConfirm(lengthMessage))
            return lengthMessage;

        return CONFIRM_MESSAGE;
    }

    // Checkfields is used for many fields (javafx)
    public static String checkMultipleFields(String[][] fields){
        String message;
        //Check si pour tout les champs, la taille est correcte et ils ne sont pas vides
        //Retournera un msg different de "confirm" sinon
        for(String[] field: fields){
            message = checkField(field);

            if(!isConfirm(message))
                return message;
        }

        return CONFIRM_MESSAGE;
    }
    
    public static boolean isConfirm(String message){

        return message.compareTo(CONFIRM_MESSAGE) == 0;
    }

    public static boolean isNumeric(String string) {

        if(string == null || string.equals("")) { // Si vide
            return false;
        }

        // Avant de parse c'est un string, parseInt transforme en Integer
        try {
            Float.parseFloat(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // This function convert DatePicker to Date
    public static Date convertDate(DatePicker date){
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    public static FXMLLoader loadContent(String fxmlName, AnchorPane anchorPane){
        /* Loading the fxml file */
        FXMLLoader loader = new FXMLLoader(ManWorkerApplication.class.getResource(fxmlName));


        /* Removing everything that's inside the anchor pane */
        for(Object c: anchorPane.getChildren().toArray()){
            anchorPane.getChildren().remove(c);
        }

        /* Adding the fxml to the anchor pane */
        try {
            anchorPane.getChildren().add(loader.load());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return loader;
    }

    public static void loadPage(String fxmlName, ActionEvent e) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(ManWorkerApplication.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), FRAME_WIDTH, FRAME_HEIGHT);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(280, 150);

        alert.show();

    }



}
