package com.example.signin;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.awt.event.ActionEvent;

public class LoanController {

    @FXML
    private CheckBox sel1;
    @FXML
    private CheckBox sel2;
    @FXML
    private CheckBox sel3;

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
