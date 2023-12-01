package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private PasswordField passwdField1, passwdField2;
    @FXML
    private Button signUpButton;
    @FXML
    private Label conditionLabel;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    private PasswordViewModel passwordViewModel = new PasswordViewModel();

    @FXML
    public void initialize(){
        passwdField1.textProperty().bindBidirectional(passwordViewModel.getPasswd1Property());
        passwdField2.textProperty().bindBidirectional(passwordViewModel.getPasswd2Property());
        conditionLabel.visibleProperty().bind(passwordViewModel.getConditionProperty());
        passwdField2.disableProperty().bind(passwordViewModel.getDisablePasswdField2Property());
        signUpButton.disableProperty().bind(passwordViewModel.getDisableSignUpProperty());
        emailField.textProperty().bindBidirectional(passwordViewModel.getEmailProperty());
        nameField.textProperty().bindBidirectional(passwordViewModel.getNameProperty());
        surnameField.textProperty().bindBidirectional(passwordViewModel.getSurnameProperty());
    }

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
