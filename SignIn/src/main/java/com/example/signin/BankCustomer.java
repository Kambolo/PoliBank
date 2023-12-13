package com.example.signin;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BankCustomer extends User implements BankOperations {

    private int accountNumber;
    private Wallet wallet;
    private int id;
    private DbController dbController;

    public class Wallet{
        private BigDecimal pln, eur, gbp, usd;

        public Wallet(){

            setPln(0);
        }
        public void setPln(double pln) {this.pln = BigDecimal.valueOf(pln);}
        public BigDecimal getPln() {return this.pln;}
        public void setEur(double eur) {this.eur = BigDecimal.valueOf(eur);}
        public void setGbp(double gbp) {this.gbp = BigDecimal.valueOf(gbp);}
        public void setUsd(double usd) {this.usd= BigDecimal.valueOf(usd);}
    }
    public BankCustomer(){
        setDbController(Main.getDbController());
        setWallet(new Wallet());
    }

    @Override
    public boolean payment(BigDecimal value) throws SQLException {

        getWallet().setPln(getWallet().getPln().doubleValue() + value.doubleValue());
        double newValue = getWallet().getPln().setScale(2).doubleValue();


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            query = "UPDATE wallet SET pln=" + newValue + " WHERE idCustomer=" + getId();
            getDbController().getStatement().executeUpdate(query);

            LocalDate localDate = LocalDate.now();
            query = "INSERT INTO registers VALUES (NULL, '%d','wplata', '%s')".formatted(getId(), localDate);
            getDbController().getStatement().executeUpdate(query);

            return true;

        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        finally {
            getDbController().getStatement().close();
            getDbController().getConnection().close();
        }
        return false;
    }

    @Override
    public boolean paycheck(BigDecimal bigDecimal) {
        return false;
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
    private Wallet getWallet(){
        return this.wallet;
    }
    private void setWallet(Wallet wallet) {this.wallet = wallet;}
    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}public int getId() {return id;}
    public void setId(int id) {this.id = id;}

}
