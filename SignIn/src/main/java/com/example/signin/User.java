package com.example.signin;

import java.math.BigDecimal;

public abstract class User {
    protected String name, lastname, email, plec, password;

    abstract public void payment(BigDecimal bigDecimal);

    abstract public void paycheck(BigDecimal bigDecimal);

    abstract public void transfer(int accountNumber);

    abstract public void getLoan();

    abstract public void makeDeposit();

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
