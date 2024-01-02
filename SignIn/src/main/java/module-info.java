module com.example.signin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.signin to javafx.fxml;
    exports com.example.signin;
}