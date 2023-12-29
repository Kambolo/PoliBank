package com.example.signin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Obiekt klasy DepositElement stanowi pojedyncza lokate utworzona przez uzytkownika
 */
public class DepositElement {
    private StringProperty sum = new SimpleStringProperty();
    private StringProperty startDate = new SimpleStringProperty();
    private StringProperty endDate = new SimpleStringProperty();
    private StringProperty percent = new SimpleStringProperty();

    /**
     * Konstruktor 4-argumentowy klasy DepositElement
     * @param sum kwota lokaty
     * @param startDate poczatek lokaty
     * @param endDate koniec lokaty
     * @param percent procent lokaty
     */
    public DepositElement(String sum, String startDate, String endDate, String percent){
        this.sum.set(sum);
        this.startDate.set(startDate);
        this.endDate.set(endDate);
        this.percent.set(percent);
    }

    public StringProperty getSum() {return sum;}
    public StringProperty getStartDate() {return startDate;}
    public StringProperty getEndDate() {return endDate;}
    public StringProperty getPercent() {return percent;}
}
