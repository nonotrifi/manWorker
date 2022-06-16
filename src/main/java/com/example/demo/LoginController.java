package com.example.demo;

import com.example.demo.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginController{

    @FXML
    public void goToRegistration(ActionEvent e) throws IOException {
        ManWorkerApplication.loadPage("registration.fxml", e);
        System.out.println("Hello");
    }

    @FXML
    public void goToHome(ActionEvent e) throws IOException {
        ManWorkerApplication.loadPage("home.fxml", e);
        ManWorkerApplication.currentUser = new User(1, "user", "admin123",
                "John", "White", "John.white@gmail.com");
    }

}