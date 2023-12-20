package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;

public class PaycheckController {
    @FXML
    private TextField paycheckValue;
    @FXML
    private Label invalidValue;
    @FXML
    private Label paycheckResult;
    private BooleanProperty showInvalidValue = new SimpleBooleanProperty();
    private BooleanProperty showPaycheckResult = new SimpleBooleanProperty();
    private BankCustomer bankCustomer;

    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        invalidValue.visibleProperty().bind(showInvalidValue);
        paycheckResult.visibleProperty().bind(showPaycheckResult);
        showInvalidValue.set(false);
        showPaycheckResult.set(false);
    }

    /**
     * Metoda wstrzyknieta do przycisku umozliwiajaca wykonanie wyplaty
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void makePaycheck(ActionEvent evt) throws IOException {
        try {
            double value = Double.parseDouble(paycheckValue.getText());
            BigDecimal bigDecimalValue = new BigDecimal(value);

            if(bankCustomer.paycheck(bigDecimalValue)){
                paycheckResult.setText("Wyplata zakonczona sukcesem! :)");
            }
            else paycheckResult.setText("Wyplata zakonczona niepowodzeniem! :(");
            showPaycheckResult.set(true);

        } catch (Exception e){
            System.out.println(e);
            showInvalidValue.set(true);
        }
    }

    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
}
