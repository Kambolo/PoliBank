package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private CheckBox female;
    @FXML
    private CheckBox male;

    public void switchToSignIn(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public boolean femaleChecked(ActionEvent evt){
        if(female.isSelected()){
            male.setSelected(false);
            return true;
        }
        male.setSelected(true);
        return false;
    }

    public boolean maleChecked(ActionEvent evt){
        if(male.isSelected()){
            female.setSelected(false);
            return true;
        }
        female.setSelected(true);
        return false;
    }
}
