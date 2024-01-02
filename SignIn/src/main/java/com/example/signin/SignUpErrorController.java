package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kontroler signUpError.fxml
 */
public class SignUpErrorController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void returnToSignIn(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
