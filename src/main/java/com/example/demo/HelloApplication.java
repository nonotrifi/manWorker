package com.example.demo;

import com.example.demo.models.Planning;
import com.example.demo.models.Team;
import com.example.demo.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/*
- Make the passwords invisibile
- Check if the textfields in the registration are empty
- Make a scene where the user can put information for a new planning
- Login and registration same color as the home
- Error text red
 */

/*
13/06/2022
- create an interface for the projects page with all the project the user created
- settings interface to change name of the user and password
- create a new team interface ( add it in the menu ) like the project interface ( like progects interface ) TeamController
- possibility to modify a planning ( add a step, add tasks to the steps, remove steps or tasks)
- fix menu ( without reloading ) -> no scene


--- Later ----
- do the database
*/

public class HelloApplication extends Application {

    static ArrayList<Planning> plannings = new ArrayList<Planning>();
    static ArrayList<Team> teams = new ArrayList<>();

    static User currentUser;

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