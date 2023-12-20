package com.example.signin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class DesktopController {
    @FXML
    private Label personalDataLabel;
    @FXML
    private Label accNrLabel;
    @FXML
    private ChoiceBox<String> currencyID;
    @FXML
    private Label valueLabel;
    private StringProperty valueProperty = new SimpleStringProperty();
    private BankCustomer bankCustomer;
    private final ObservableList<String> choices = FXCollections.observableArrayList("PLN", "EUR", "GBP", "USD");

    @FXML
    public  void initialize(){
        setBankCustomer(Main.getBankCustomer());
        personalDataLabel.setText(getBankCustomer().getName() + " " + getBankCustomer().getLastname());
        accNrLabel.setText(getBankCustomer().getAccNumber());
        valueLabel.textProperty().bind(valueProperty);

        currencyID.setValue(choices.get(0));
        currencyID.setItems(choices);
        valueProperty.set(String.valueOf(getBankCustomer().getWallet().getPln()));

        currencyID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                switch (currencyID.getValue()) {
                    case "PLN" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getPln()));
                    case "EUR" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getEur()));
                    case "GBP" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getGbp()));
                    case "USD" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getUsd()));
                    default -> valueLabel.setText("???");
                };
            }
        });
    }
    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
}
