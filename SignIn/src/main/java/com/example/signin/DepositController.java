package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Kontroler deposis.fxml
 */
public class DepositController {
    @FXML
    private Label errorLabel;
    @FXML
    private Label invalidValue;
    @FXML
    private TextField valueTextField;
    @FXML
    private Label depositComplete;
    @FXML
    private CheckBox option1;
    @FXML
    private CheckBox option2;
    @FXML
    private CheckBox option3;
    @FXML
    private CheckBox option4;
    private BooleanProperty errorLabelProperty = new SimpleBooleanProperty();
    private BooleanProperty invalidValueProperty = new SimpleBooleanProperty();
    private BooleanProperty depositCompleteProperty = new SimpleBooleanProperty();
    private BankCustomer bankCustomer;

    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        errorLabel.visibleProperty().bind(errorLabelProperty);
        invalidValue.visibleProperty().bind(invalidValueProperty);
        depositComplete.visibleProperty().bind(depositCompleteProperty);
        errorLabelProperty.set(false);
        invalidValueProperty.set(false);
        depositCompleteProperty.set(false);
    }

    /**
     * Metoda wstrzyknieta do przycisku umozliwiajaca utworzenie lokaty
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void makeDeposit(ActionEvent evt) throws IOException {

        try{
            double value = Double.parseDouble(valueTextField.getText());
            BigDecimal bigDecimal = new BigDecimal(value);

            LocalDate endDate = LocalDate.now();
            double percent;

            if(option1.isSelected()){
                percent = 0.02;
                endDate.plusMonths(1);
            }
            else if(option2.isSelected()){
                percent = 0.15;
                endDate.plusMonths(3);
            }
            else if(option3.isSelected()){
                percent = 0.01;
                endDate.plusMonths(6);
            }
            else{
                percent = 0.005;
                endDate.plusYears(1);
            }

            if(!getBankCustomer().makeDeposit(bigDecimal, LocalDate.now(), endDate, percent)) errorLabelProperty.set(true);

            depositCompleteProperty.set(true);

        } catch (Exception e){
            invalidValueProperty.set(true);
        }
    }

    /**
     * Metoda wstrzyknieta do przycisku option1 umozliwiajaca zaznaczenie jednego check boxa
     * @param evt zdażenie(wciśnięcie przycisku)
     */
    public void selectOption1(ActionEvent evt){
        option1.setSelected(true);
        option2.setSelected(false);
        option3.setSelected(false);
        option4.setSelected(false);
    }

    /**
     * Metoda wstrzyknieta do przycisku option2 umozliwiajaca zaznaczenie jednego check boxa
     * @param evt zdażenie(wciśnięcie przycisku)
     */
    public void selectOption2(ActionEvent evt){
        option1.setSelected(false);
        option2.setSelected(true);
        option3.setSelected(false);
        option4.setSelected(false);
    }

    /**
     * Metoda wstrzyknieta do przycisku option3 umozliwiajaca zaznaczenie jednego check boxa
     * @param evt zdażenie(wciśnięcie przycisku)
     */
    public void selectOption3(ActionEvent evt){
        option1.setSelected(false);
        option2.setSelected(false);
        option3.setSelected(true);
        option4.setSelected(false);
    }

    /**
     * Metoda wstrzyknieta do przycisku option4 umozliwiajaca zaznaczenie jednego check boxa
     * @param evt zdażenie(wciśnięcie przycisku)
     */
    public void selectOption4(ActionEvent evt){
        option1.setSelected(false);
        option2.setSelected(false);
        option3.setSelected(false);
        option4.setSelected(true);
    }

    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
}
