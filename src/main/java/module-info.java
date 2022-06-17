module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;


    opens com.example.demo;
    opens com.example.demo.models;
    exports com.example.demo;
    exports com.example.demo.models;
}