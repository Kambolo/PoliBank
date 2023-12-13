package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuController {
    @FXML
    private BorderPane contentContainer;

    @FXML
    public void initialize() throws IOException {
        BorderPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("desktop.fxml"));
        } catch(IOException e){
            e.printStackTrace();
        }
        contentContainer.setCenter(pane);

    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void exchangePanel(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exchange.fxml"));
        contentContainer.setCenter(loader.load());
    }

    public void dektopPanel(ActionEvent evt) throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("desktop.fxml"));
        contentContainer.setCenter(loader.load());
    }

    public void paymentPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
        contentContainer.setCenter(loader.load());
    }
}
