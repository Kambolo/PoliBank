package com.example.signin;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class LoanController {

    @FXML
    private CheckBox sel1;
    @FXML
    private CheckBox sel2;
    @FXML
    private CheckBox sel3;

    @FXML
    private TextField loanValue;

    @FXML
    private Label rata;
    @FXML
    private Label rrso;

    @FXML
    public void initialize(){
        rata.setText("0");
        rrso.setText("0");

        loanValue.textProperty().addListener((observable, oldValue, newValue)-> {
            try {
                if (newValue != null && !newValue.isEmpty()) {
                    double val = Double.parseDouble(newValue);
                } else {
                    rata.setText("0");
                    rrso.setText("0");
                }
            } catch (Exception ex) {
                rata.setText("invalid input");
                rrso.setText("invalid input");
            }
        })
    ;}

    public void selectSel1(javafx.event.ActionEvent actionEvent){
        sel1.setSelected(true);
        sel2.setSelected(false);
        sel3.setSelected(false);
    }

    public void selectSel2(javafx.event.ActionEvent actionEvent){
        sel2.setSelected(true);
        sel1.setSelected(false);
        sel3.setSelected(false);
    }

    public void selectSel3(javafx.event.ActionEvent actionEvent){
        sel3.setSelected(true);
        sel2.setSelected(false);
        sel1.setSelected(false);
    }



}
