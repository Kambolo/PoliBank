module com.example.signin {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.signin to javafx.fxml;
    exports com.example.signin;
}