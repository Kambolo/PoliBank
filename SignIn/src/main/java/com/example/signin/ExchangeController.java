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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.signin.Main.getBankCustomer;
import static com.example.signin.Main.getDbController;


/**
 * Klasa ExchangeController jest kontrolerem interfejsu użytkownika dla pliku FXML "exchange.fxml".
 * Odpowiada za obsługę operacji wymiany walut.
 */
public class ExchangeController implements Initializable {

    // Deklaracje kontrolek JavaFX, oznaczone adnotacją @FXML
    @FXML
    private ChoiceBox<String> currencyID;
    @FXML
    private Button submit;
    @FXML
    private TextField exchangeValue;
    @FXML
    private Label afterExchangeValue;
    @FXML
    private Label currency;
    @FXML
    private Label eurRate;
    @FXML
    private Label gbpRate;
    @FXML
    private Label usdRate;
    @FXML
    private Label plnID;
    @FXML
    private Label eurID;
    @FXML
    private Label gbpID;
    @FXML
    private Label usdID;

    // Inne pola klasy
    private final ObservableList<String> choices = FXCollections.observableArrayList("EUR","GBP", "USD");
    private Currencies currencies;

    /**
     * Metoda inicjalizacyjna, wywoływana podczas inicjalizacji kontrolera.
     * Inicjalizuje widok oraz ustawia domyślne wartości kontrolek.
     * @param url Adres URL pliku FXML.
     * @param resourceBundle Zasób związany z danym obiektem ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currencies = new Currencies();
        currencyID.setValue(choices.get(0));
        currencyID.setItems(choices);

        eurRate.setText(String.valueOf(currencies.getEurSell()));
        gbpRate.setText(String.valueOf(currencies.getGbpSell()));
        usdRate.setText(String.valueOf(currencies.getUsdSell()));

        plnID.setText(String.valueOf(getBankCustomer().getWallet().getPln()));
        eurID.setText(String.valueOf(getBankCustomer().getWallet().getEur()));
        gbpID.setText(String.valueOf(getBankCustomer().getWallet().getGbp()));
        usdID.setText(String.valueOf(getBankCustomer().getWallet().getUsd()));

        // Dodanie obserwatora dla pola wymiany walut, reagującego na zmiany wartości
        exchangeValue.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null && !newValue.isEmpty()) {
                    double val = Double.parseDouble(newValue);
                    double result = getResult(val);
                    afterExchangeValue.setText(String.valueOf(result));
                } else {
                    afterExchangeValue.setText("0");
                }
            } catch (NumberFormatException e) {
                afterExchangeValue.setText("Invalid Input");
            }
        });

        // Dodanie obserwatora dla wyboru waluty, reagującego na zmiany wybranej waluty
        currencyID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                currency.setText(newValue);
                if (exchangeValue.getText() == null)  afterExchangeValue.setText("0");
                else {
                    double val = Double.parseDouble(exchangeValue.getText());
                    double result = getResult(val);
                    afterExchangeValue.setText(String.valueOf(result));
                }
            } catch (Exception e) {
                System.out.println("Error!");
            }
        });
    }

    /**
     * Metoda obliczająca wartość wymiany walut na podstawie podanej wartości wejściowej.
     * @param val Wartość wejściowa.
     * @return Wartość wymiany.
     */
    private double getResult(double val) {
        double multiplier = switch (currencyID.getValue()) {
            case "EUR" -> currencies.getEurSell();
            case "GBP" -> currencies.getGbpSell();
            case "USD" -> currencies.getUsdSell();
            default -> 0.0d;
        };
        BigDecimal result = BigDecimal.valueOf(val / multiplier).setScale(2, RoundingMode.HALF_DOWN);
        return result.doubleValue();
    }

    /**
     * Obsługa zdarzenia naciśnięcia przycisku "Zatwierdź".
     * @param evt Zdarzenie akcji przycisku.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void clickedOnExchange(ActionEvent evt) throws ClassNotFoundException, SQLException {
        String firstCurr = "pln";
        String secondCurr = currencyID.getValue().toLowerCase();

        if (exchangeValue.getText().isEmpty()) return;

        double firstV = Double.parseDouble(exchangeValue.getText());
        double secondV = Double.parseDouble(afterExchangeValue.getText());
        getBankCustomer().exchange(BigDecimal.valueOf(firstV), secondCurr, BigDecimal.valueOf(secondV));

        plnID.setText(String.valueOf(getBankCustomer().getWallet().getPln()));
        eurID.setText(String.valueOf(getBankCustomer().getWallet().getEur()));
        gbpID.setText(String.valueOf(getBankCustomer().getWallet().getGbp()));
        usdID.setText(String.valueOf(getBankCustomer().getWallet().getUsd()));
    }
}
