package com.example.signin;

import java.io.Serializable;
import java.time.LocalDate;

public class LoanElement implements Serializable {

    private double loanValue;
    private LocalDate startDate;
    private LocalDate endDate;
    private double percent;
    private int installmentCount;
    private LocalDate currDate;
    private int installmentsMax;
    public LoanElement(LocalDate startDate, LocalDate endDate, double val, double perc){
        this.setLoanValue(val);
        this.setStartDate(LocalDate.now());
        this.setEndDate(LocalDate.now());
        this.setPercent(perc);
        installmentCount = 0;
        currDate = startDate.plusMonths(1);

        if(percent == 0.05) setInstallmentsMax(6);
        else if(percent == 0.07) setInstallmentsMax(12);
        else setInstallmentsMax(18);
    }

    public double getLoanValue() {
        return loanValue;
    }

    public double getInstallmentValue(){
        return ((loanValue*percent) + loanValue)/ getInstallmentsMax();
    }
    public void setLoanValue(double loanValue) {
        this.loanValue = loanValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public boolean payInstallment(){
        installmentCount++;
        currDate = currDate.plusMonths(1);
        if(installmentsMax<=installmentCount) return false;
        return true;
    }
    public LocalDate getCurrInstallmentDate(){
        return currDate;
    }
    public int getInstallmentCount(){
        return installmentCount;
    }

    public int getInstallmentsMax() {
        return installmentsMax;
    }

    public void setInstallmentsMax(int installmentsMax) {
        this.installmentsMax = installmentsMax;
    }
}
