package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;

public class TransferController {
    @FXML
    private TextField transferValue;
    @FXML
    private TextField accNumber;
    @FXML
    private Label invalidValue;
    @FXML
    private Label transferResult;
    private BooleanProperty showInvalidValue = new SimpleBooleanProperty();
    private BooleanProperty showTransferResult = new SimpleBooleanProperty();
    private BankCustomer bankCustomer;

    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        invalidValue.visibleProperty().bind(showInvalidValue);
        transferResult.visibleProperty().bind(showTransferResult);
        showInvalidValue.set(false);
        showTransferResult.set(false);
    }
    public void makeTransfer(ActionEvent evt) throws IOException{
        try {
            double value = Double.parseDouble(transferValue.getText());
            BigDecimal bigDecimalValue = new BigDecimal(value);

            if(getBankCustomer().transfer(accNumber.getText(), bigDecimalValue)){
                transferResult.setText("Przelew zakonczony sukcesem! :)");
            }
            else {
                transferResult.setText("Przelew zakonczony niepowodzeniem! :(");
            }
            showTransferResult.set(true);

        } catch (Exception e){
            System.out.println(e);
            showInvalidValue.set(true);
        }
    }
    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}

}
