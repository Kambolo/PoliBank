
package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.example.signin.Main.*;

/**
 * Kontroler obsługujący operacje związane z udzielaniem pożyczek.
 */
public class LoanController {

    @FXML
    public TextField loanValue;
    @FXML
    private CheckBox sel1;
    @FXML
    private CheckBox sel2;
    @FXML
    private CheckBox sel3;
    @FXML
    private Label rata;
    @FXML
    private Label loanError;

    private BankCustomer bankCustomer;

    private int howManyMonths;
    private double per;

    /**
     * Inicjalizuje kontroler ustawiając klienta banku, domyślne wartości pól oraz nasłuchuje zmiany wartości pożyczki.
     */
    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        sel1.setSelected(true);
        sel2.setSelected(false);
        sel3.setSelected(false);

        loanError.setText("");

        rata.setText("0");
        loanValue.textProperty().addListener((observable, oldValue, newValue)->{
            displayInstallment();
        });
    }

    /**
     * Udziela pożyczki na podstawie wybranego rodzaju oraz wartości wpisanej przez użytkownika.
     * @param evt Zdarzenie akcji
     * @throws ClassNotFoundException Występuje, gdy nie można znaleźć określonej klasy
     * @throws SQLException Występuje, gdy wystąpi problem z bazą danych
     */
    public void takeLoan(ActionEvent evt) throws ClassNotFoundException, SQLException {
        boolean firstLoan=true;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            ResultSet resultSet;
            query = "SELECT * FROM loans WHERE idCustomer=" + getBankCustomer().getId();
            resultSet = getDbController().getStatement().executeQuery(query);

            if(resultSet.next()) firstLoan = false;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            getDbController().getStatement().close();
            getDbController().getConnection().close();
        }

        if(firstLoan){
            try{
                if(loanValue.getText() != null || !loanValue.getText().isEmpty()) {
                    double val = Double.parseDouble(loanValue.getText());

                    LoanElement loan = new LoanElement(LocalDate.now(), LocalDate.now().plusMonths(howManyMonths), val, per);
                    getBankCustomer().getLoan(BigDecimal.valueOf(loan.getLoanValue()), loan.getStartDate(), loan.getEndDate(), loan.getPercent());
                    getBankCustomer().setActiveLoan(loan);
                    loanError.setText("Pożyczka została udzielona!");
                }
            } catch(Exception e){
                loanError.setText(e.getMessage());
            }
        }
        else{
            loanError.setText("Nie możesz wziąć kolejnej pożyczki!");
        }
    }

    /**
     * Ustawia wybrany rodzaj pożyczki na sel1 i aktualizuje widok.
     * @param actionEvent Zdarzenie akcji
     */
    public void selectSel1(ActionEvent actionEvent){
        sel1.setSelected(true);
        sel2.setSelected(false);
        sel3.setSelected(false);

        displayInstallment();
    }

    /**
     * Ustawia wybrany rodzaj pożyczki na sel2 i aktualizuje widok.
     * @param actionEvent Zdarzenie akcji
     */
    public void selectSel2(ActionEvent actionEvent){
        sel2.setSelected(true);
        sel1.setSelected(false);
        sel3.setSelected(false);

        displayInstallment();
    }

    /**
     * Ustawia wybrany rodzaj pożyczki na sel3 i aktualizuje widok.
     * @param actionEvent Zdarzenie akcji
     */
    public void selectSel3(ActionEvent actionEvent){
        sel3.setSelected(true);
        sel2.setSelected(false);
        sel1.setSelected(false);

        displayInstallment();
    }

    /**
     * Oblicza wartość raty miesięcznej na podstawie wybranego rodzaju pożyczki i wartości pożyczki.
     * @param val Wartość pożyczki
     * @return Wartość raty miesięcznej
     */
    private double valueOfInstallment(double val){
        if(sel1.isSelected()){
            per = 0.05;
            howManyMonths = 6;
        }
        else if (sel2.isSelected()) {
            per = 0.07;
            howManyMonths = 12;
        }
        else{
            per = 0.09;
            howManyMonths = 18;
        }
        return ((val * per) + val) /howManyMonths;
    }

    /**
     * Aktualizuje widok, wyświetlając wartość raty miesięcznej.
     */
    private void displayInstallment(){
        try{
            if(loanValue.getText() != null || !loanValue.getText().isEmpty()){
                double val = Double.parseDouble(loanValue.getText());

                rata.setText(String.format("%.2f", valueOfInstallment(val)));
            }
            else{
                rata.setText("0");
            }
        } catch(Exception e){
            rata.setText("Nieprawidłowe dane");
        }
    }
}
