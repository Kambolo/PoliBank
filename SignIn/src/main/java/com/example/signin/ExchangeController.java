package com.example.signin;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.signin.Main.getDbController;

public class ExchangeController implements Initializable {
    @FXML
    private ChoiceBox<String> currencyID;

    private final ObservableList<String> choices = FXCollections.observableArrayList("EUR","GBP", "USD");

    @FXML
    private Button submit;
    @FXML
    private TextField exchangeValue;
    @FXML
    private Label afterExchangeValue;
    Currencies currencies;
    @FXML
    private Label currency;
    @FXML
    private Label eurRate;
    @FXML
    private Label gbpRate;
    @FXML
    private Label usdRate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        currencies = new Currencies();
        currencyID.setValue(choices.get(1));
        currencyID.setItems(choices);

        eurRate.setText(String.valueOf(currencies.getEurSell()));
        gbpRate.setText(String.valueOf(currencies.getGbpSell()));
        usdRate.setText(String.valueOf(currencies.getUsdSell()));

        exchangeValue.textProperty().addListener((observable, oldValue, newValue)->{
            try{
                if (newValue != null && !newValue.isEmpty()) {
                    double val = Double.parseDouble(newValue);
                    double result = getResult(val);

                    afterExchangeValue.setText(String.valueOf(result));
                } else {
                    afterExchangeValue.setText("0");
                }
            }catch(NumberFormatException e){
                afterExchangeValue.setText("Invalid Input");
            }
        });

        currencyID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            try{
                currency.setText(newValue);
                if(exchangeValue.getText() == null)  afterExchangeValue.setText("0");
                else{
                    double val = Double.parseDouble(exchangeValue.getText());
                    double result = getResult(val);
                    afterExchangeValue.setText(String.valueOf(result));
                }
            }catch(Exception e){
                System.out.println("Error!");
            }
        });
    }

    private double getResult(double val) {
        double multiplier = switch (currencyID.getValue()) {
            case "EUR" -> currencies.getEurSell();
            case "GBP" -> currencies.getGbpSell();
            case "USD" -> currencies.getUsdSell();
            default -> 0.0d;
        };
        return val * multiplier;
    }

    public void clickedOnExchange(ActionEvent evt) throws ClassNotFoundException, SQLException {

    }

}
