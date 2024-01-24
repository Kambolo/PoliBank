package com.example.signin;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Interfejs definiujący operacje związane z procesem rejestracji.
 */
public interface SignUpOperation {

    /**
     * Metoda do rejestracji nowego użytkownika.
     * @throws SQLException Błąd podczas operacji na bazie danych.
     * @throws IOException Błąd wejścia/wyjścia podczas operacji na plikach.
     */
    void signUp() throws SQLException, IOException;
}