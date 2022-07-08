package com.example.demo.tests;

import com.example.demo.LoginController;
import com.example.demo.ManWorkerApplication;
import com.example.demo.RegistrationController;
import com.example.demo.Utils;
import com.example.demo.backend.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;


public class ManWorkerTest {
    LoginController loginController;
    RegistrationController registrationController;

    @Before
    public void setUp(){
        ManWorkerApplication.connectToDatabase();
        loginController = new LoginController();
        registrationController = new RegistrationController();
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
        String message = registrationController.registerMessage("mariam", "ricccardo", "bianchi",
                "riccardo@gmail.com", "123456", "123456");

        assertEquals(0, message.compareTo("Confirm"));

        message = registrationController.registerMessage("m", "ricccardo", "bianchi",
                "riccardo@gmail.com", "123456", "123456");

        assertEquals(0, message.compareTo("username cannot be less than 5 and greater than 25 characters."));
    }

    @Test
    public void testIsValidated() throws SQLException {
        String loginMessage = loginController.isValidated("mariam", "123456");
        assertEquals(0, loginMessage.compareTo("Confirm"));

        loginMessage = loginController.isValidated("mariam", "12345");
        assertEquals(0, loginMessage.compareTo("Password is wrong"));

        loginMessage = loginController.isValidated("mar", "12345");
        assertEquals(0, loginMessage.compareTo("Username is wrong"));
    }


}
