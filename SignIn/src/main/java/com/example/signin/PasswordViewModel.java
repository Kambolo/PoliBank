package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PasswordViewModel {
    private StringProperty passwd1Property = new SimpleStringProperty();
    private StringProperty passwd2Property = new SimpleStringProperty();
    private BooleanProperty disableSignUpProperty = new SimpleBooleanProperty(true);
    private BooleanProperty disablePasswdField2Property = new SimpleBooleanProperty(true);
    private BooleanProperty conditionProperty = new SimpleBooleanProperty(true);
    public PasswordViewModel(){
        disablePasswdField2Property.bind(passwd1Property.length().lessThan(8));
        conditionProperty.bind(passwd1Property.isNotEmpty());
        disableSignUpProperty.bind((getPasswd2Property().isNotEqualTo(getPasswd1Property())).or(getPasswd2Property().length().lessThan(8)));
    }

    public BooleanProperty getDisableSignUpProperty() {
        return disableSignUpProperty;
    }

    public BooleanProperty getDisablePasswdField2Property() {
        return disablePasswdField2Property;
    }

    public BooleanProperty getConditionProperty() {
        return conditionProperty;
    }

    public StringProperty getPasswd1Property() {
        return passwd1Property;
    }

    public StringProperty getPasswd2Property() {
        return passwd2Property;
    }

}
