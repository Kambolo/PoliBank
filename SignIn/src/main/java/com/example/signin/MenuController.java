package com.example.signin;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class MenuController {
    @FXML
    private BorderPane contentContainer;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    private StringProperty timeProperty= new SimpleStringProperty();
    private StringProperty dateProperty= new SimpleStringProperty();
    private AnimationTimer clock;

    @FXML
    public void initialize() throws IOException {
        BorderPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("desktop.fxml"));
        } catch(IOException e){
            e.printStackTrace();
        }
        getContentContainer().setCenter(pane);

        dateLabel.textProperty().bind(dateProperty);
        timeLabel.textProperty().bind(timeProperty);

        clock = new Timer();
        clock.start();
    }

    private class Timer extends AnimationTimer {
        @Override
        public void handle(long now){
            LocalTime time = LocalTime.now();
            String text = time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
            timeProperty.set(text);
            dateProperty.set(String.valueOf(LocalDate.now()));
        }
    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void exchangePanel(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exchange.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    public void dektopPanel(ActionEvent evt) throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("desktop.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    public void paymentPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
        getContentContainer().setCenter(loader.load());
    }
    public void paycheckPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paycheck.fxml"));
        getContentContainer().setCenter(loader.load());
    }
    public void transferPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transfer.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    public void historyPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("history.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    public BorderPane getContentContainer() {
        return contentContainer;
    }

}
