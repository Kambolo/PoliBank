package com.example.signin;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.example.signin.Main.getDbController;

public class BankCustomer extends User implements BankOperations {

    private int accountNumber;
    private Wallet wallet;
    private int id;
    private DbController dbController;


    public class Wallet{
        private BigDecimal pln, eur, gbp, usd;

        public Wallet() throws SQLException {
            getCurrencies();
        }
        private void getCurrencies() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
                getDbController().setStatement(getDbController().getConnection().createStatement());

                String query;
                ResultSet resultSet;

                query = "SELECT pln FROM wallet WHERE idCustomer=%d".formatted(id);
                resultSet = getDbController().getStatement().executeQuery(query);
                resultSet.next();
                pln = BigDecimal.valueOf(resultSet.getDouble("pln"));

                query = "SELECT gbp FROM wallet WHERE idCustomer=%d".formatted(id);
                resultSet = getDbController().getStatement().executeQuery(query);
                resultSet.next();
                gbp = BigDecimal.valueOf(resultSet.getDouble("gbp"));

                query = "SELECT eur FROM wallet WHERE idCustomer=%d".formatted(id);
                resultSet = getDbController().getStatement().executeQuery(query);
                resultSet.next();
                eur = BigDecimal.valueOf(resultSet.getDouble("eur"));

                query = "SELECT usd FROM wallet WHERE idCustomer=%d".formatted(id);
                resultSet = getDbController().getStatement().executeQuery(query);
                resultSet.next();
                usd = BigDecimal.valueOf(resultSet.getDouble("usd"));

            } catch (Exception e) {
                System.out.println(e);
            }finally {
                getDbController().getStatement().close();
                getDbController().getConnection().close();
            }
        }
        private void setCurrency(String currency, double value) throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
                getDbController().setStatement(getDbController().getConnection().createStatement());

                String query;
                ResultSet resultSet;

                query = "UPDATE wallet SET "+currency+"='"+value+"' WHERE idCustomer="+getId();
                getDbController().getStatement().executeUpdate(query);
            } catch (Exception e) {
                System.out.println(e);
            }finally {
                getDbController().getStatement().close();
                getDbController().getConnection().close();
            }

            switch (currency){
                case "pln":
                    pln = BigDecimal.valueOf(value);
                    break;
                case "eur":
                    eur = BigDecimal.valueOf(value);
                    break;
                case "gbp":
                    gbp = BigDecimal.valueOf(value);
                    break;
                case "usd":
                    usd = BigDecimal.valueOf(value);
                    break;
            }
        }
        public void setPln(double pln) throws SQLException {setCurrency("pln", pln);}
        public BigDecimal getPln() {return this.pln;}
        public void setEur(double eur) throws SQLException {setCurrency("eur", eur);}
        public BigDecimal getEur() {return this.eur;}
        public void setGbp(double gbp) throws SQLException {setCurrency("gbp", gbp);}
        public BigDecimal getGbp() {return this.gbp;}
        public void setUsd(double usd) throws SQLException {setCurrency("usd", usd);}
        public BigDecimal getUsd() {return this.usd;}
    }
    public BankCustomer(int id, String email, String name, String lastname, String password) throws SQLException {
        setId(id);
        setDbController(Main.getDbController());
        setWallet(new Wallet());
        setEmail(email);
        setName(name);
        setLastname(lastname);
        setPassword(password);
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
    public void exchange(String firstCurr, BigDecimal firstValue, String secondCurr, BigDecimal secondValue) throws SQLException {
        BigDecimal temp;

        switch(firstCurr){
            case "pln":
                temp = BigDecimal.valueOf(getWallet().getPln().doubleValue() - firstValue.doubleValue());
                getWallet().setPln(temp.doubleValue());
                break;

            case "eur":
                temp = BigDecimal.valueOf(getWallet().getEur().doubleValue() - firstValue.doubleValue());
                getWallet().setEur(temp.doubleValue());
                break;

            case "usd":
                temp = BigDecimal.valueOf(getWallet().getUsd().doubleValue() - firstValue.doubleValue());
                getWallet().setUsd(temp.doubleValue());
                break;

            case "gbp":
                temp = BigDecimal.valueOf(getWallet().getGbp().doubleValue() - firstValue.doubleValue());
                getWallet().setGbp(temp.doubleValue());
                break;
        }

        switch(secondCurr){
            case "pln":
                temp = BigDecimal.valueOf(getWallet().getPln().doubleValue() + secondValue.doubleValue());
                getWallet().setPln(temp.doubleValue());
                break;

            case "eur":
                temp = BigDecimal.valueOf(getWallet().getEur().doubleValue() + secondValue.doubleValue());
                getWallet().setEur(temp.doubleValue());
                break;

            case "usd":
                temp = BigDecimal.valueOf(getWallet().getUsd().doubleValue() + secondValue.doubleValue());
                getWallet().setUsd(temp.doubleValue());
                break;

            case "gbp":
                temp = BigDecimal.valueOf(getWallet().getGbp().doubleValue() + secondValue.doubleValue());
                getWallet().setGbp(temp.doubleValue());
                break;
        }
    }
    public Wallet getWallet(){return this.wallet;}
    private void setWallet(Wallet wallet) {this.wallet = wallet;}
    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}public int getId() {return id;}
    public void setId(int id) {this.id = id;}

}
