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
        String message1 = Utils.checkIfBlank("", "username");
        assertEquals(0, message1.compareTo("username field cannot be blank"));

        String message2 = Utils.checkIfBlank("Mark", "username");
        assertEquals(0, message2.compareTo("Confirm"));
    }

    @Test
    public void testLength(){
        String message1 = Utils.checkLength("m", "username");
        assertEquals(0, message1.compareTo("username cannot be less than 5 and greator than 25 characters."));

        String message2 = Utils.checkIfBlank("riccardo", "username");
        assertEquals(0, message2.compareTo("Confirm"));
    }

    @Test
    public void testRegisterMessage() throws SQLException {
        String message = registrationController.registerMessage("mariam", "ricccardo", "bianchi",
                "riccardo@gmail.com", "123456", "123456");

        assertEquals(0, message.compareTo("Confirm"));
    }
}
