package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TransferController {
    @FXML
    private TextField transferValue;
    @FXML
    private TextField accNumber;
    @FXML
    private Label invalidValue;
    @FXML
    private Label invalidAcc;
    @FXML
    private Label transferResult;
    private BooleanProperty showInvalidValue = new SimpleBooleanProperty();
    private BooleanProperty showInvalidAcc = new SimpleBooleanProperty();
    private BooleanProperty showTransferResult = new SimpleBooleanProperty();
    private BankCustomer bankCustomer;

    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        invalidValue.visibleProperty().bind(showInvalidValue);
        invalidAcc.visibleProperty().bind(showInvalidAcc);
        transferResult.visibleProperty().bind(showTransferResult);
        showInvalidValue.set(false);
        showInvalidAcc.set(false);
        showTransferResult.set(false);
    }
    public void makeTransfer(ActionEvent evt) throws IOException{

    }
    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
}
