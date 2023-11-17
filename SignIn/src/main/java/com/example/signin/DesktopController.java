package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DesktopController {
    @FXML
    private BorderPane contentContainer;

    public void exchangePanel(ActionEvent evt) throws IOException {
        System.out.println("k");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exchange.fxml"));
        contentContainer.setCenter(loader.load());
    }

    @FXML
    public void initialize() throws IOException {

    }

}
