package com.example.signin;

import java.math.BigDecimal;

public class LoggedUser extends User implements StartOperations{

    private int accountNumber;
    private Wallet wallet;

    public class Wallet{
        private BigDecimal pln, eur, gbp, usd;

        public void setPln(double pln) {this.pln = BigDecimal.valueOf(pln);}
        public void setEur(double eur) {this.eur = BigDecimal.valueOf(eur);}
        public void setGbp(double gbp) {this.gbp = BigDecimal.valueOf(gbp);}
        public void setUsd(double usd) {this.usd= BigDecimal.valueOf(usd);}
    }

    public Wallet getWallet(){
        return this.wallet;
    }

    @Override
    public void payment(BigDecimal bigDecimal) {

    }

    @Override
    public void paycheck(BigDecimal bigDecimal) {

    }

    @Override
    public void transfer(int accountNumber) {

    }

    @Override
    public void getLoan() {

    }

    @Override
    public void makeDeposit() {

    }

    @Override
    public LoggedUser signIn() {
        LoggedUser loggedUser = new LoggedUser();
        return loggedUser;
    }

    @Override
    public void SignUp() {

    }
}
