package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Klasa odpowiedzialna za widocznosc komunikatow oraz dostepnosc pol TextField w SettingsController
 */
public class NewPasswordViewModel {
    private StringProperty oldPasswdProperty = new SimpleStringProperty();
    private StringProperty newPasswd1Property = new SimpleStringProperty();
    private StringProperty newPasswd2Property = new SimpleStringProperty();
    private BooleanProperty disableConfirmButtonProperty = new SimpleBooleanProperty(true);
    private BooleanProperty disableNewPassword2Property= new SimpleBooleanProperty(true);
    private BooleanProperty conditionProperty = new SimpleBooleanProperty(true);

    public NewPasswordViewModel(){
        disableNewPassword2Property.bind(newPasswd1Property.length().lessThan(8));
        conditionProperty.bind(newPasswd1Property.isNotEmpty());
        disableConfirmButtonProperty.bind((getNewPasswd2Property().isNotEqualTo(getNewPasswd1Property())).or(getNewPasswd2Property().length().lessThan(8)));

    }

    public StringProperty getOldPasswdProperty() {
        return oldPasswdProperty;
    }

    public StringProperty getNewPasswd1Property() {
        return newPasswd1Property;
    }

    public StringProperty getNewPasswd2Property() {
        return newPasswd2Property;
    }

    public BooleanProperty getDisableConfirmButtonProperty() {
        return disableConfirmButtonProperty;
    }

    public BooleanProperty getDisableNewPassword2Property() {
        return disableNewPassword2Property;
    }

    public BooleanProperty getConditionProperty() {
        return conditionProperty;
    }
}
