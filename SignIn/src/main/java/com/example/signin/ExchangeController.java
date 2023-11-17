package com.example.signin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ExchangeController implements Initializable {
    @FXML
    private ChoiceBox<String> currency1ID;
    @FXML
    private ChoiceBox<String> currency2ID;

    ObservableList<String> choices = FXCollections.observableArrayList("PLN","EUR","GBP", "USD");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currency1ID.setValue(choices.get(0));
        currency1ID.setItems(choices);

        currency2ID.setValue(choices.get(1));
        currency2ID.setItems(choices);
    }
}
