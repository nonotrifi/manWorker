module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    requires javafx.graphics;
    requires java.sql;
    requires org.testng;
    requires junit;
    requires org.junit.jupiter.api;

    exports com.example.demo;
    opens com.example.demo;
    opens com.example.demo.tests;
    exports com.example.demo.tests;
    exports com.example.demo.backend;
    opens com.example.demo.backend;
}

