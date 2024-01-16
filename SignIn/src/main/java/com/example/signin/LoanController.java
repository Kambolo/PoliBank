package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoanController {

    @FXML
    public TextField loanValue;
    @FXML
    private CheckBox sel1;
    @FXML
    private CheckBox sel2;
    @FXML
    private CheckBox sel3;

    @FXML
    private Label rata;
    @FXML
    private Label rrso;

    @FXML
    public void initialize(){
        sel1.setSelected(true);
        sel2.setSelected(false);
        sel3.setSelected(false);

        rrso.setText("0");
        rata.setText("0");
        loanValue.textProperty().addListener((observable, oldValue, newValue)->{
            displayRataAndRrso(Double.parseDouble(loanValue.getText()));
        });
    }
    
    
    public void selectSel1(ActionEvent actionEvent){
        sel1.setSelected(true);
        sel2.setSelected(false);
        sel3.setSelected(false);

        displayRataAndRrso(Double.parseDouble(loanValue.getText()));

    }

    public void selectSel2(ActionEvent actionEvent){
        sel2.setSelected(true);
        sel1.setSelected(false);
        sel3.setSelected(false);

        displayRataAndRrso(Double.parseDouble(loanValue.getText()));
    }

    public void selectSel3(ActionEvent actionEvent){
        sel3.setSelected(true);
        sel2.setSelected(false);
        sel1.setSelected(false);

        displayRataAndRrso(Double.parseDouble(loanValue.getText()));
    }


    private double valueOfRata(double val){
        double per = 0.0d;
        int howMany = 0;
        if(sel1.isSelected()){
            per = 0.05;
            howMany = 6;
        }
        else if (sel2.isSelected()) {
            per = 0.07;
            howMany = 12;
        }
        else{
            per = 0.09;
            howMany = 18;
        }

        return (val * per) /howMany;
    }

    private double valueOfRrso(double val){
        double per = 0.0d;
        int howMany = 0;
        if(sel1.isSelected()){
            per = 0.05;
            howMany = 6;
        }
        else if (sel2.isSelected()) {
            per = 0.07;
            howMany = 12;
        }
        else{
            per = 0.09;
            howMany = 18;
        }

        return per * (365/howMany*30) * 100;
    }

    private void displayRataAndRrso(double val){
        try{
            rrso.setText(String.format("%.2f", valueOfRrso(val)));
            rata.setText(String.format("%.2f", valueOfRata(val)));
        }catch(Exception e){
            rrso.setText("Invalid Input");
            rata.setText("Invalid Input");
        }
    }

}
