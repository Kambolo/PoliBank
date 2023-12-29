package com.example.signin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Obiekt klasy HistoryElement stanowi pojedyncza operacje wykonana przez uzytkownika
 */
public class HistoryElement {
    private StringProperty operation = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();

    /**
     * Konstruktor dwuargumentowy klasy HistoryElement
     * @param operation nazwa operacji
     * @param date data przeprowadzenia operacji
     */
    public HistoryElement(String operation, String date){
        this.operation.set(operation);
        this.date.set(date);
    }

    public StringProperty getOperation() {
        return operation;
    }
    public StringProperty getDate() {
        return date;
    }
}
