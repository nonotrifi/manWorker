package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
- Make the passwords invisibile
- Check if the textfields in the registration are empty
- Make a scene where the user can put information for a new planning
- Login and registration same color as the home
- Error text red
 */

/*
13/06/2022
- check if the budget that the user wrote is a number
- when the user clicks "OK" we have to create a new project (maybe save the project in an array in the homeApplication)
with all the information the user put
- create an interface for the projects page with all the project the user created
- settings interface to change name of the user and password
- create a new team interface ( add it in the menu ) like the project interface
- possibility to modify a planning ( add a step, add tasks to the steps, remove steps or tasks)
- do the database
*/

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), LoginController.FRAME_HEIGHT, LoginController.FRAME_WIDTH);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    
}