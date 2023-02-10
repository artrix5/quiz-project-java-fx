module com.example.kvizprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;

    exports com.example.kvizprojekt.database;
    opens com.example.kvizprojekt.database to javafx.fxml;
    exports com.example.kvizprojekt.main.controllers;
    opens com.example.kvizprojekt.main.controllers to javafx.fxml;
    exports com.example.kvizprojekt.main;
    opens com.example.kvizprojekt.main to javafx.fxml;
}