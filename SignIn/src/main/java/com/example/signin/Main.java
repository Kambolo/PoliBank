package com.example.signin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private static DbController dbController;
    @Override
    public void start(Stage stage) throws IOException {
        setDbController(new DbController());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PoliBank");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {launch();}

    public static DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {Main.dbController = dbController;}
}