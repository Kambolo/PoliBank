module com.example.signin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.signin to javafx.fxml;
    exports com.example.signin;
}