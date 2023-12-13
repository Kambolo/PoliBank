package com.example.signin;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface BankOperations {
    public boolean payment(BigDecimal value) throws SQLException;

    public boolean paycheck(BigDecimal value) throws SQLException;

    public boolean transfer(String accountNumber, BigDecimal value) throws SQLException;

    public void getLoan();

    public void makeDeposit();
}
