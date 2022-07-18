package com.example.demo;
import javafx.event.ActionEvent;
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
    public static String checkIfBlank(String[] field){
        if(field == null)
            return "An error occurred";

        String fieldName = field[0], fieldValue = field[1];

        if(fieldName == null)
            return "An error occurred";

        if(fieldValue == null)
            return fieldName + " field cannot be empty";

        if (fieldValue.isBlank()) {
            return fieldName + " field cannot be blank";
        }

        return CONFIRM_MESSAGE;
    }

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

        // grace a la method static dans utils
        blankMessage = Utils.checkIfBlank(field);
        lengthMessage = Utils.checkLength(field);

        if(!isConfirm(blankMessage))
            return blankMessage;

        if(!isConfirm(lengthMessage))
            return lengthMessage;

        return CONFIRM_MESSAGE;
    }

    // Checkfields it's used for many fields (javafx)
    public static String checkMultipleFields(String[][] fields){
        String message;

        // for-each java
        for(String[] field: fields){
            // grace a la method static dans utils
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

        if(string == null || string.equals("")) {
            return false;
        }

        // Avant de parse c'est un string. l'objectif avec parseInt est de le transformer en Integer
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
        /* Loading the fxml file FXMLLoader is just a JavaFx class that we use to load a new fxml file, getResource taking fxml */
        FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlName));


        /* Removing everything that's inside the anchor pane, getChildren() getting everything from anchorPane and toArray()
        * Converting the type to an Array(); Object c because I don't know the type */
        for(Object c: anchorPane.getChildren().toArray()){
            anchorPane.getChildren().remove(c);
        }

        /* Adding the fxml to the anchor pane, load() get the content inside the fxml */
        try {
            anchorPane.getChildren().add(loader.load());
        } catch (IOException ioe) {
            // maye error inside the fxml
            ioe.printStackTrace();
        }

        return loader;
    }

    public static void loadPage(String fxmlName, ActionEvent e) throws IOException{
        // the fxml is inside th folder "ressources" folder le PATH "ManWorkerApplication.class.getResource(fxmlName)"
        FXMLLoader fxmlLoader = new FXMLLoader(ManWorkerApplication.class.getResource(fxmlName));

        // what's inside the window
        Scene scene = new Scene(fxmlLoader.load(), FRAME_WIDTH, FRAME_HEIGHT);

        // it's the window |
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // puting the scene inside the window
        stage.setScene(scene);
        // showing the window make it visible
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
