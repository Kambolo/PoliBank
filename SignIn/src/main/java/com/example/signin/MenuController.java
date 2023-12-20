package com.example.signin;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class MenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private BorderPane contentContainer;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    private StringProperty timeProperty= new SimpleStringProperty();
    private StringProperty dateProperty= new SimpleStringProperty();
    private AnimationTimer clock;
    private BankCustomer bankCustomer;

    @FXML
    public void initialize() throws IOException {
        setBankCustomer(Main.getBankCustomer());
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


    /**
     * Klasa zagniezdzona umozliwiajaca implementacje zegara w menu.fxml
     */
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
     * Zamiana zawartośći głownego kontenera z treścią na panel wymiany walut
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void exchangePanel(ActionEvent evt) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exchange.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią na pulpit
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void dektopPanel(ActionEvent evt) throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("desktop.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią na panel wplat
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void paymentPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią na panel wyplat
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void paycheckPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paycheck.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią na panel przelewow
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void transferPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transfer.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    /**
     * Zamiana zawartośći głownego kontenera z treścią na panel historii operacji uzytkownika
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws IOException
     */
    public void historyPanel(ActionEvent evt) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("history.fxml"));
        getContentContainer().setCenter(loader.load());
    }

    /**
     * Wywolanie metody logOut w celu wylogowania uzytkownika
     * @param evt zdażenie(wciśnięcie przycisku)
     * @throws SQLException
     * @throws IOException
     */
    public void logOut(ActionEvent evt) throws SQLException, IOException {
        getBankCustomer().logOut();

        root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public BorderPane getContentContainer() {return contentContainer;}
    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}

}
