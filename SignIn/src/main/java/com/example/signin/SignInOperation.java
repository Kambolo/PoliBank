package com.example.signin;

import java.sql.SQLException;

/**
 * Interfejs definiujący operacje związane z procesem logowania.
 */
public interface SignInOperation {

    /**
     * Metoda do logowania użytkownika.
     * @param username Adres email użytkownika.
     * @param password Hasło użytkownika.
     * @throws ClassNotFoundException Błąd podczas ładowania klasy sterownika JDBC.
     * @throws SQLException Błąd podczas operacji na bazie danych.
     */
    void signIn(String username, String password) throws ClassNotFoundException, SQLException;
}