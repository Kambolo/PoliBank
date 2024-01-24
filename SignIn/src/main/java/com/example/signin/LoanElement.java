package com.example.signin;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Klasa LoanElement reprezentuje element związany z kredytem.
 * Implementuje interfejs Serializable, umożliwiając serializację obiektów tej klasy.
 */
public class LoanElement implements Serializable {

    // Pola klasy
    private double loanValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private double percent;
    private int installmentCount;
    private LocalDate currDate;
    private int installmentsMax;

    /**
     * Konstruktor klasy LoanElement, inicjalizuje obiekt kredytu.
     * @param startDate Data rozpoczęcia kredytu.
     * @param endDate Data zakończenia kredytu.
     * @param val Wartość kredytu.
     * @param perc Oprocentowanie kredytu.
     */
    public LoanElement(LocalDate startDate, LocalDate endDate, double val, double perc) {
        this.setLoanValue(val);
        this.setStartDate(LocalDate.now());
        this.setEndDate(LocalDate.now());
        this.setPercent(perc);
        installmentCount = 0;
        currDate = startDate.plusMonths(1);

        if (percent == 0.05) setInstallmentsMax(6);
        else if (percent == 0.07) setInstallmentsMax(12);
        else setInstallmentsMax(18);
    }

    /**
     * Pobiera wartość kredytu.
     * @return Wartość kredytu.
     */
    public double getLoanValue() {
        return loanValue;
    }

    /**
     * Pobiera wartość raty kredytu.
     * @return Wartość raty kredytu.
     */
    public double getInstallmentValue() {
        return ((loanValue * percent) + loanValue) / getInstallmentsMax();
    }

    /**
     * Ustawia wartość kredytu.
     * @param loanValue Wartość kredytu.
     */
    public void setLoanValue(double loanValue) {
        this.loanValue = loanValue;
    }

    /**
     * Pobiera datę rozpoczęcia kredytu.
     * @return Data rozpoczęcia kredytu.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Ustawia datę rozpoczęcia kredytu.
     * @param startDate Data rozpoczęcia kredytu.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Pobiera datę zakończenia kredytu.
     * @return Data zakończenia kredytu.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Ustawia datę zakończenia kredytu.
     * @param endDate Data zakończenia kredytu.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Pobiera procent oprocentowania kredytu.
     * @return Procent oprocentowania kredytu.
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Ustawia procent oprocentowania kredytu.
     * @param percent Procent oprocentowania kredytu.
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * Płaci ratę kredytu.
     * @return True, jeśli kredyt nie został jeszcze spłacony; False, jeśli kredyt został spłacony.
     */
    public boolean payInstallment() {
        installmentCount++;
        currDate = currDate.plusMonths(1);
        return installmentCount <= installmentsMax;
    }

    /**
     * Pobiera bieżącą datę spłaty raty kredytu.
     * @return Bieżąca data spłaty raty kredytu.
     */
    public LocalDate getCurrInstallmentDate() {
        return currDate;
    }

    /**
     * Pobiera liczbę spłaconych rat kredytu.
     * @return Liczba spłaconych rat kredytu.
     */
    public int getInstallmentCount() {
        return installmentCount;
    }

    /**
     * Pobiera maksymalną liczbę rat kredytu.
     * @return Maksymalna liczba rat kredytu.
     */
    public int getInstallmentsMax() {
        return installmentsMax;
    }

    /**
     * Ustawia maksymalną liczbę rat kredytu.
     * @param installmentsMax Maksymalna liczba rat kredytu.
     */
    public void setInstallmentsMax(int installmentsMax) {
        this.installmentsMax = installmentsMax;
    }
}
