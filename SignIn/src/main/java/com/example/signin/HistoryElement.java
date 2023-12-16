package com.example.signin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HistoryElement {
    private StringProperty operation = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();

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
