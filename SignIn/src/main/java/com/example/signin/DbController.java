package com.example.signin;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Klasa DbController reprezentuje kontroler dostępu do bazy danych.
 * Zawiera metody do zarządzania połączeniem oraz operacjami na bazie danych.
 */
public class DbController {

    // Adres URL bazy danych
    private String url;

    // Nazwa użytkownika do połączenia z bazą danych
    private String username;

    // Hasło użytkownika do połączenia z bazą danych
    private String password;

    // Połączenie z bazą danych
    private Connection connection;

    // Obiekt do wykonania operacji na bazie danych
    private Statement statement;

    /**
     * Konstruktor domyślny, inicjalizuje parametry połączenia do bazy danych.
     */
    public DbController() {
        this.url = "jdbc:mysql://localhost:3306/bankdb";
        this.username = "root";
        this.password = "";
    }

    /**
     * Metoda zwracająca adres URL bazy danych.
     *
     * @return Adres URL bazy danych.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Metoda ustawiająca adres URL bazy danych.
     *
     * @param url Nowy adres URL bazy danych.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Metoda zwracająca nazwę użytkownika do połączenia z bazą danych.
     *
     * @return Nazwa użytkownika do połączenia z bazą danych.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Metoda ustawiająca nazwę użytkownika do połączenia z bazą danych.
     *
     * @param username Nowa nazwa użytkownika do połączenia z bazą danych.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Metoda zwracająca hasło użytkownika do połączenia z bazą danych.
     *
     * @return Hasło użytkownika do połączenia z bazą danych.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metoda ustawiająca hasło użytkownika do połączenia z bazą danych.
     *
     * @param password Nowe hasło użytkownika do połączenia z bazą danych.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Metoda zwracająca obiekt połączenia z bazą danych.
     *
     * @return Obiekt połączenia z bazą danych.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Metoda ustawiająca obiekt połączenia z bazą danych.
     *
     * @param connection Nowy obiekt połączenia z bazą danych.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Metoda zwracająca obiekt do wykonania operacji na bazie danych.
     *
     * @return Obiekt do wykonania operacji na bazie danych.
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Metoda ustawiająca obiekt do wykonania operacji na bazie danych.
     *
     * @param statement Nowy obiekt do wykonania operacji na bazie danych.
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}

