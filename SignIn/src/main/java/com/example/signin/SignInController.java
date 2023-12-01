package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void switchToSignUp(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMenu(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(this.getClass().getResource("menu.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
