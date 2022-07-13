package com.example.demo.tests;

import com.example.demo.*;
import com.example.demo.backend.Planning;
import com.example.demo.backend.Team;
import com.example.demo.backend.User;
import com.example.demo.exceptions.PlanningException;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ManWorkerTest {
    LoginController loginController;
    RegistrationController registrationController;
    AddStepsController addStepsController;

    @Before
    public void setUp(){
        ManWorkerApplication.connectToDatabase();
        loginController = new LoginController();
        registrationController = new RegistrationController();
        addStepsController = new AddStepsController();
    }

    @Test
    public void testBlank(){
        String[] field = {"username", "   "};
        String blankMessage = Utils.checkIfBlank(field);
        assertEquals(0, blankMessage.compareTo("username field cannot be blank"));

        field[1] = "Riccardo";

        blankMessage = Utils.checkIfBlank(field);
        assertEquals(0, blankMessage.compareTo("Confirm"));
    }

    @Test
    public void testLength(){
        String[] field = {"username", "m"};

        String lengthMessage = Utils.checkLength(field);
        assertEquals(0, lengthMessage.compareTo("username cannot be less than 5 and greater than 25 characters."));

        field[1] = "riccardo";

        lengthMessage = Utils.checkIfBlank(field);
        assertEquals(0, lengthMessage.compareTo("Confirm"));
    }

    @Test
    public void testRegisterMessage(){
        String message = registrationController.checkRegistrationFields("mariam", "ricccardo", "bianchi",
                "riccardo@gmail.com", "123456", "123456");

        assertEquals(0, message.compareTo("Confirm"));

        message = registrationController.checkRegistrationFields("m", "ricccardo", "bianchi",
                "riccardo@gmail.com", "123456", "123456");

        assertEquals(0, message.compareTo("username cannot be less than 5 and greater than 25 characters."));
    }

    @Test
    public void testIsValidated() throws SQLException {
        String loginMessage = loginController.isValidated("mairam", "123456");
        assertEquals(0, loginMessage.compareTo("Confirm"));

        loginMessage = loginController.isValidated("mairam", "1234566");
        assertEquals(0, loginMessage.compareTo("Password is wrong"));

        loginMessage = loginController.isValidated("mar", "12345");
        assertEquals(0, loginMessage.compareTo("Username is wrong"));
    }

    @Test
    public void testAddStepsSetUpException(){
        assertThrows(PlanningException.class, () -> addStepsController.setUp(null));
    }



}
