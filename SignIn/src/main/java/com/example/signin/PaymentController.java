package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;

public class PaymentController {

    @FXML
    private TextField paymentValue;
    @FXML
    private Label invalidValue;
    @FXML
    private Label paymentResult;
    private BooleanProperty showInvalidValue = new SimpleBooleanProperty();
    private BooleanProperty showPaymentResult = new SimpleBooleanProperty();
    private BankCustomer bankCustomer;

    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        invalidValue.visibleProperty().bind(showInvalidValue);
        paymentResult.visibleProperty().bind(showPaymentResult);
        showInvalidValue.set(false);
        showPaymentResult.set(false);
    }

    /**
     * Metoda wstrzyknieta do przycisku umozliwiajaca wykonanie wplaty
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void makePayment(ActionEvent evt) throws IOException {
        try {
            double value = Double.parseDouble(paymentValue.getText());
            BigDecimal bigDecimalValue = new BigDecimal(value);

            if(bankCustomer.payment(bigDecimalValue)){
                paymentResult.setText("Wplata zakonczona sukcesem! :)");
            }
            else paymentResult.setText("Wplata zakonczona niepowodzeniem! :(");
            showPaymentResult.set(true);

        } catch (Exception e){
            System.out.println(e);
            showInvalidValue.set(true);
        }
    }

    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
}
