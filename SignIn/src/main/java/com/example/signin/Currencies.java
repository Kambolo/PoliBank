package com.example.signin;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import static com.example.signin.Main.getDbController;

/**
 * Klasa reprezentująca kursy walut, pobierane z bazy danych.
 */
public class Currencies {

    // Lista kursów walut: eurSell, eurBuy, gbpSell, gbpBuy, usdSell, usdBuy
    private ArrayList<Double> currenciesList;

    // Kurs kupna i sprzedaży euro
    private double eurBuy;
    private double eurSell;

    // Kurs kupna i sprzedaży funta brytyjskiego
    private double gbpBuy;
    private double gbpSell;

    // Kurs kupna i sprzedaży dolara amerykańskiego
    private double usdBuy;
    private double usdSell;

    /**
     * Konstruktor klasy Currencies. Inicjalizuje listę kursów walut i pobiera wartości z bazy danych.
     */
    Currencies() {
        // Inicjalizacja listy kursów
        setCurrenciesList(new ArrayList<Double>(6));

        try {
            // Połączenie z bazą danych
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            // Pobranie kursów euro z bazy danych
            query = "SELECT eur FROM currencies";
            resultSet = getDbController().getStatement().executeQuery(query);
            int temp = 0;

            // Dodanie dwóch wartości kursu euro do listy
            while (resultSet.next() && temp < 2) {
                getCurrenciesList().addLast(resultSet.getDouble("eur"));
                temp++;
            }
            temp = 0;

            // Pobranie kursów funta brytyjskiego z bazy danych
            query = "SELECT gbp FROM currencies";
            resultSet = getDbController().getStatement().executeQuery(query);

            // Dodanie dwóch wartości kursu funta brytyjskiego do listy
            while (resultSet.next() && temp < 2) {
                getCurrenciesList().addLast(resultSet.getDouble("gbp"));
                temp++;
            }
            temp = 0;

            // Pobranie kursów dolara amerykańskiego z bazy danych
            query = "SELECT usd FROM currencies";
            resultSet = getDbController().getStatement().executeQuery(query);

            // Dodanie dwóch wartości kursu dolara amerykańskiego do listy
            while (resultSet.next() && temp < 2) {
                getCurrenciesList().addLast(resultSet.getDouble("usd"));
                temp++;
            }

            // Przypisanie wartości z listy do odpowiednich zmiennych
            eurSell = currenciesList.get(0);
            eurBuy = currenciesList.get(1);
            gbpSell = currenciesList.get(2);
            gbpBuy = currenciesList.get(3);
            usdSell = currenciesList.get(4);
            usdBuy = currenciesList.get(5);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Metoda prywatna zwracająca listę kursów walut.
     *
     * @return Lista kursów walut.
     */
    private ArrayList<Double> getCurrenciesList() {
        return currenciesList;
    }

    /**
     * Metoda prywatna ustawiająca listę kursów walut.
     *
     * @param currenciesList Nowa lista kursów walut.
     */
    private void setCurrenciesList(ArrayList<Double> currenciesList) {
        this.currenciesList = currenciesList;
    }

    /**
     * Metoda zwracająca kurs kupna euro.
     *
     * @return Kurs kupna euro.
     */
    public double getEurBuy() {
        return eurBuy;
    }

    /**
     * Metoda zwracająca kurs sprzedaży euro.
     *
     * @return Kurs sprzedaży euro.
     */
    public double getEurSell() {
        return eurSell;
    }

    /**
     * Metoda zwracająca kurs kupna funta brytyjskiego.
     *
     * @return Kurs kupna funta brytyjskiego.
     */
    public double getGbpBuy() {
        return gbpBuy;
    }

    /**
     * Metoda zwracająca kurs sprzedaży funta brytyjskiego.
     *
     * @return Kurs sprzedaży funta brytyjskiego.
     */
    public double getGbpSell() {
        return gbpSell;
    }

    /**
     * Metoda zwracająca kurs kupna dolara amerykańskiego.
     *
     * @return Kurs kupna dolara amerykańskiego.
     */
    public double getUsdBuy() {
        return usdBuy;
    }

    /**
     * Metoda zwracająca kurs sprzedaży dolara amerykańskiego.
     *
     * @return Kurs sprzedaży dolara amerykańskiego.
     */
    public double getUsdSell() {
        return usdSell;
    }
}

