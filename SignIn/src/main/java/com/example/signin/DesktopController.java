package com.example.signin;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DesktopController{
    @FXML
    private BorderPane contentContainer;
    private String[] curr = {"PLN", "EUR", "GBP", "USD"};
    /**
     * Zamiana zawartośći głownego kontenera z treścią
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void exchangePanel(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exchange.fxml"));
        contentContainer.setCenter(loader.load());
    }


}
