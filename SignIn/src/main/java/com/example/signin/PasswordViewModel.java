package com.example.signin;

import javafx.beans.binding.BooleanBinding;
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

    public void setDisableSignUpProperty(BooleanProperty disableSignUpProperty) {
        this.disableSignUpProperty = disableSignUpProperty;
    }

    public BooleanProperty getDisablePasswdField2Property() {
        return disablePasswdField2Property;
    }

    public void setDisablePasswdField2Property(BooleanProperty disablePasswdField2Property) {
        this.disablePasswdField2Property = disablePasswdField2Property;
    }

    public BooleanProperty getConditionProperty() {
        return conditionProperty;
    }

    public void setConditionProperty(BooleanProperty conditionProperty) {
        this.conditionProperty = conditionProperty;
    }

    public StringProperty getPasswd1Property() {
        return passwd1Property;
    }

    public void setPasswd1Property(StringProperty passwd1Property) {
        this.passwd1Property = passwd1Property;
    }

    public StringProperty getPasswd2Property() {
        return passwd2Property;
    }

    public void setPasswd2Property(StringProperty passwd2Property) {
        this.passwd2Property = passwd2Property;
    }
}
