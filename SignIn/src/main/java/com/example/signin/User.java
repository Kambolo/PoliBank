package com.example.signin;

import java.io.Serializable;

/**
 * Klasa abstrakcyjna stanowiaca szkielet BankCustomer do dziedziczenia
 */
public abstract class User implements Serializable {
    private String name, lastname, email, plec, password;

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getLastname() {return lastname;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPlec() {return plec;}

    public void setPlec(String plec) {this.plec = plec;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}
}
