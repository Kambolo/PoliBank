package com.example.signin;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.example.signin.Main.getDbController;
import static java.math.BigDecimal.ROUND_DOWN;

public class BankCustomer extends User implements BankOperations {
    private Wallet wallet;
    private int id;
    private DbController dbController;
    private String accNumber;


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
    public BankCustomer(int id, String email, String name, String lastname, String password, String accNumber) throws SQLException {
        setId(id);
        setDbController(Main.getDbController());
        setWallet(new Wallet());
        setEmail(email);
        setName(name);
        setLastname(lastname);
        setPassword(password);
        setAccNumber(accNumber);
    }

    @Override
    public boolean payment(BigDecimal value) throws SQLException {

        double newValue = getWallet().getPln().setScale(2).add(value.setScale(2)).doubleValue();

        getWallet().setPln(newValue);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            LocalDate localDate = LocalDate.now();
            query = "INSERT INTO registers VALUES (NULL, '%d','wplata_%s', '%s')".formatted(getId(), value.setScale(2, ROUND_DOWN).doubleValue(), localDate);
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
    public boolean paycheck(BigDecimal value) throws SQLException {

        if (!isEnough(value.doubleValue())) return false;

        double newValue = getWallet().getPln().setScale(2).subtract(value.setScale(2)).doubleValue();

        getWallet().setPln(newValue);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            LocalDate localDate = LocalDate.now();
            query = "INSERT INTO registers VALUES (NULL, '%d','wyplata_%s', '%s')".formatted(getId(), value.setScale(2, ROUND_DOWN).doubleValue(), localDate);
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
    public boolean transfer(String accountNumber, BigDecimal value) throws SQLException {

        if (!isEnough(value.doubleValue()) || !ifExist(accountNumber)) return false;

        double newValue1 = getWallet().getPln().setScale(2).subtract(value.setScale(2)).doubleValue();
        double newValue2 = value.setScale(2).doubleValue();

        try{
            getWallet().setPln(newValue1);

            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            query = "UPDATE wallet SET pln=pln+" + newValue2 + " WHERE idCustomer in (SELECT idCustomer FROM customers WHERE accNumber='%s')".formatted(accountNumber);
            getDbController().getStatement().executeUpdate(query);

            LocalDate localDate = LocalDate.now();
            query = "INSERT INTO registers VALUES (NULL, '%d','przelew_%s', '%s')".formatted(getId(), value.setScale(2, ROUND_DOWN).doubleValue(), localDate);
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

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            LocalDate localDate = LocalDate.now();
            query = "INSERT INTO registers VALUES (NULL, '%d','wymiana_%s', '%s')".formatted(getId(), firstValue.setScale(2, ROUND_DOWN).doubleValue(), localDate);
            getDbController().getStatement().executeUpdate(query);


        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        finally {
            getDbController().getStatement().close();
            getDbController().getConnection().close();
        }
    }
    public Wallet getWallet(){return this.wallet;}
    private void setWallet(Wallet wallet) {this.wallet = wallet;}
    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getAccNumber() {return accNumber;}
    public void setAccNumber(String accNumber) {this.accNumber = accNumber;}
    private boolean isEnough(double value) throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;
            query = "SELECT pln FROM wallet WHERE idCustomer="+ getId();
            resultSet = getDbController().getStatement().executeQuery(query);
            resultSet.next();

            if(resultSet.getDouble(1) < value) return false;
            return  true;

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
    private boolean ifExist(String accountNumber) throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            query = "SELECT COUNT(accNumber) FROM customers WHERE accNumber='%s'".formatted(accountNumber);
            resultSet = getDbController().getStatement().executeQuery(query);
            resultSet.next();
            if(resultSet.getInt(1) == 0) return false;
            return  true;

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

}

