package com.example.signin;

import java.math.BigDecimal;

public interface BankOperations {
    public void payment(BigDecimal bigDecimal);

    public void paycheck(BigDecimal bigDecimal);

    public void transfer(int accountNumber);

    public void getLoan();

    public void makeDeposit();
}
