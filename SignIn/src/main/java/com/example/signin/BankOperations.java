package com.example.signin;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public interface BankOperations {
    public boolean payment(BigDecimal value) throws SQLException;

    public boolean paycheck(BigDecimal value) throws SQLException;

    public boolean transfer(String accountNumber, BigDecimal value) throws SQLException;

    public void getLoan();

    public boolean makeDeposit(BigDecimal value, LocalDate startdate, LocalDate endDate, double percent) throws SQLException;

    public void exchange(BigDecimal firstValue, String secondCurr, BigDecimal secondValue) throws SQLException;
}
