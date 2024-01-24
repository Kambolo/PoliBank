package com.example.signin;

import java.io.Serializable;

/**
 * Klasa abstrakcyjna stanowiąca szkielet BankCustomer do dziedziczenia.
 * Implementuje interfejs Serializable.
 */
public abstract class User implements Serializable {

    private String name, lastname, email, gender, password;

    /**
     * Pobiera imię użytkownika.
     * @return Imię użytkownika.
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia imię użytkownika.
     * @param name Imię użytkownika.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Pobiera nazwisko użytkownika.
     * @return Nazwisko użytkownika.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Ustawia nazwisko użytkownika.
     * @param lastname Nazwisko użytkownika.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Pobiera adres email użytkownika.
     * @return Adres email użytkownika.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres email użytkownika.
     * @param email Adres email użytkownika.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Pobiera płeć użytkownika.
     * @return Płeć użytkownika.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Ustawia płeć użytkownika.
     * @param gender Płeć użytkownika.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Pobiera hasło użytkownika.
     * @return Hasło użytkownika.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia hasło użytkownika.
     * @param password Hasło użytkownika.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}