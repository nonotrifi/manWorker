module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


//    requires java.sql;
    requires javafx.graphics;
    requires java.sql;

    opens com.example.demo;
    opens com.example.demo.models;
    exports com.example.demo;
}

