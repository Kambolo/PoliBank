package com.example.signin;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
/**
 * Interfejs definiujący operacje bankowe, które mogą być realizowane przez klientów.
 */
public interface BankOperations {

    /**
     * Metoda umożliwiająca dokonanie płatności z konta klienta.
     *
     * @param value Wartość płatności.
     * @return true, jeśli operacja została zrealizowana pomyślnie; false, jeśli brakuje środków na koncie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public boolean payment(BigDecimal value) throws SQLException;

    /**
     * Metoda umożliwiająca dokonanie wpłaty na konto klienta.
     *
     * @param value     Wartość wpłaty.
     * @return true, jeśli operacja została zrealizowana pomyślnie; false, jeśli brakuje środków na koncie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public boolean paycheck(BigDecimal value) throws SQLException;

    /**
     * Metoda umożliwiająca dokonanie przelewu na inne konto.
     *
     * @param accountNumber Numer konta, na które ma być dokonany przelew.
     * @param value         Wartość przelewu.
     * @return true, jeśli operacja została zrealizowana pomyślnie; false, jeśli brakuje środków na koncie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public boolean transfer(String accountNumber, BigDecimal value) throws SQLException;

    /**
     * Metoda umożliwiająca klientowi zaciągnięcie pożyczki.
     *
     * @param value     Wartość pożyczki.
     * @param startdate Data rozpoczęcia pożyczki.
     * @param endDate   Data zakończenia pożyczki.
     * @param percent   Procentowe oprocentowanie pożyczki.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public void getLoan(BigDecimal value, LocalDate startdate, LocalDate endDate, double percent) throws SQLException;

    /**
     * Metoda umożliwiająca klientowi dokonanie wpłaty na konto depozytowe.
     *
     * @param value     Wartość depozytu.
     * @param startDate Data rozpoczęcia depozytu.
     * @param endDate   Data zakończenia depozytu.
     * @param percent   Procentowe oprocentowanie depozytu.
     * @return true, jeśli operacja została zrealizowana pomyślnie; false, jeśli brakuje środków na koncie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public boolean makeDeposit(BigDecimal value, LocalDate startDate, LocalDate endDate, double percent) throws SQLException;

    /**
     * Metoda umożliwiająca klientowi wymianę jednej waluty na inną.
     *
     * @param firstValue   Wartość waluty do wymiany.
     * @param secondCurr   Druga waluta, na którą wymieniamy.
     * @param secondValue  Wartość wymienianej waluty w drugiej walucie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public void exchange(BigDecimal firstValue, String secondCurr, BigDecimal secondValue) throws SQLException;
}

