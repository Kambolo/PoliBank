package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.example.signin.Main.*;

/**
 * Kontroler obsługujący interakcje użytkownika z widokiem singIn.fxml.
 */
public class SignInController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    Label wrongData;

    /**
     * Przełącza na widok rejestracji (signUp.fxml).
     * @param evt Zdarzenie akcji przycisku "Zarejestruj się".
     * @throws IOException Błąd podczas ładowania pliku FXML.
     */
    public void switchToSignUp(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        scene = new Scene(root);
        stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Obsługuje logowanie użytkownika.
     * @param evt Zdarzenie akcji przycisku "Zaloguj się".
     * @throws SQLException Błąd podczas operacji na bazie danych.
     * @throws ClassNotFoundException Błąd podczas ładowania klasy sterownika JDBC.
     */
    public void logIn(ActionEvent evt) throws SQLException, ClassNotFoundException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if(email.equals("") || password.equals("")){
            wrongData.setText("Zły email lub hasło!");
            return;
        }

        SignInOperation signInOperation = new SignInOperation() {
            @Override
            public void signIn(String username, String password) throws ClassNotFoundException, SQLException {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
                    getDbController().setStatement(getDbController().getConnection().createStatement());

                    String query;
                    ResultSet resultSet;

                    query = "SELECT password FROM customers WHERE email='%s'".formatted(email);
                    resultSet = getDbController().getStatement().executeQuery(query);

                    String validPassword = "";
                    if(resultSet.next()){
                        validPassword = resultSet.getString("password");
                    }

                    if(validPassword.equals(password)){
                        query = "SELECT idCustomer FROM customers WHERE email='%s'".formatted(email);
                        resultSet = getDbController().getStatement().executeQuery(query);

                        resultSet.next();
                        int id = resultSet.getInt("idCustomer");

                        query = "SELECT name FROM customers WHERE email='%s'".formatted(email);
                        resultSet = getDbController().getStatement().executeQuery(query);

                        resultSet.next();
                        String name = resultSet.getString("name");

                        query = "SELECT lastname FROM customers WHERE email='%s'".formatted(email);
                        resultSet = getDbController().getStatement().executeQuery(query);

                        resultSet.next();
                        String lastname = resultSet.getString("lastname");

                        query = "SELECT accNumber FROM customers WHERE email='%s'".formatted(email);
                        resultSet = getDbController().getStatement().executeQuery(query);

                        resultSet.next();
                        String accNr = resultSet.getString("accNumber");

                        LocalDate date = LocalDate.now();

                        query = "INSERT INTO registers VALUES (NULL, '%d', 'zalogowano', '%s')".formatted(id, date);
                        getDbController().getStatement().executeUpdate(query);

                        User customer = new BankCustomer(id, email, name, lastname, validPassword, accNr);
                        setBankCustomer((BankCustomer) customer); //wywołanie polimorficzne
                        switchToDesktop(evt);
                    }
                    else{
                        wrongData.setText("Zły email lub hasło!");
                    }

                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    getDbController().getStatement().close();
                    getDbController().getConnection().close();
                }
            }
        };

        signInOperation.signIn(email, password);
    }

    /**
     * Przełącza na widok głównego menu (menu.fxml).
     * @param evt Zdarzenie akcji przycisku.
     * @throws IOException Błąd podczas ładowania pliku FXML.
     */
    private void switchToDesktop(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(this.getClass().getResource("menu.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Przełącza na widok odzyskiwania hasła (forgottenPassword.fxml).
     * @param evt Zdarzenie akcji przycisku.
     * @throws IOException Błąd podczas ładowania pliku FXML.
     */
    public void switchToFrogottenPass(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(this.getClass().getResource("forgottenPassword.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
