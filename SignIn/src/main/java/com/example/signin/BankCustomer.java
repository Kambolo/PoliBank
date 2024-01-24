package com.example.signin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Klasa implementujaca uzytkownika banku
 * @inherits User
 * @implements BankOperations
 * @implements Serializable
 */
public class BankCustomer extends User implements BankOperations, Serializable {
    /**
     * Portfel klienta przechowujący saldo w różnych walutach.
     */
    private Wallet wallet;

    /**
     * Unikalny identyfikator klienta.
     */
    private int id;

    /**
     * Kontroler bazy danych (DbController), oznaczony jako transient, aby nie był serializowany.
     */
    private transient DbController dbController;

    /**
     * Numer konta klienta.
     */
    private String accNumber;

    /**
     * Flaga informująca, czy klient jest zalogowany.
     */
    private boolean ifLogOut;

    /**
     * Aktywny element pożyczki przypisany do klienta.
     */
    private LoanElement activeLoan;

    /**
     * Klasa wewnętrzna reprezentująca portfel klienta, implementująca interfejs Serializable.
     */
    public class Wallet implements Serializable{
        /**
         * Saldo w polskich złotych (PLN).
         */
        private BigDecimal pln;

        /**
         * Saldo w euro (EUR).
         */
        private BigDecimal eur;

        /**
         * Saldo w funtach brytyjskich (GBP).
         */
        private BigDecimal gbp;

        /**
         * Saldo w dolarach amerykańskich (USD).
         */
        private BigDecimal usd;

        /**
         * Konstruktor portfela, inicjalizujący salda walut poprzez pobranie danych z bazy danych.
         *
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
        public Wallet() throws SQLException {
            getCurrencies();
        }

        /**
         * Metoda prywatna pobierająca salda walut z bazy danych i inicjalizująca nimi pola obiektu.
         *
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
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
        /**
         * Metoda prywatna aktualizująca saldo wybranej waluty w bazie danych oraz w polu obiektu.
         *
         * @param currency Waluta do zaktualizowania.
         * @param value    Nowa wartość salda.
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
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
        /**
         * Metoda ustawiająca saldo w polskich złotych (PLN) w portfelu klienta.
         *
         * @param pln Nowa wartość salda w PLN.
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
        public void setPln(double pln) throws SQLException {
            setCurrency("pln", pln);
        }

        /**
         * Metoda pobierająca saldo w polskich złotych (PLN) z portfela klienta.
         *
         * @return Saldo w PLN.
         */
        public BigDecimal getPln() {
            return this.pln;
        }

        /**
         * Metoda ustawiająca saldo w euro (EUR) w portfelu klienta.
         *
         * @param eur Nowa wartość salda w EUR.
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
        public void setEur(double eur) throws SQLException {
            setCurrency("eur", eur);
        }

        /**
         * Metoda pobierająca saldo w euro (EUR) z portfela klienta.
         *
         * @return Saldo w EUR.
         */
        public BigDecimal getEur() {
            return this.eur;
        }

        /**
         * Metoda ustawiająca saldo w funtach brytyjskich (GBP) w portfelu klienta.
         *
         * @param gbp Nowa wartość salda w GBP.
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
        public void setGbp(double gbp) throws SQLException {
            setCurrency("gbp", gbp);
        }

        /**
         * Metoda pobierająca saldo w funtach brytyjskich (GBP) z portfela klienta.
         *
         * @return Saldo w GBP.
         */
        public BigDecimal getGbp() {
            return this.gbp;
        }

        /**
         * Metoda ustawiająca saldo w dolarach amerykańskich (USD) w portfelu klienta.
         *
         * @param usd Nowa wartość salda w USD.
         * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
         */
        public void setUsd(double usd) throws SQLException {
            setCurrency("usd", usd);
        }

        /**
         * Metoda pobierająca saldo w dolarach amerykańskich (USD) z portfela klienta.
         *
         * @return Saldo w USD.
         */
        public BigDecimal getUsd() {
            return this.usd;
        }

    }

    /**
     * Konstruktor klasy BankCustomer
     * @param id id uzytkownika
     * @param email email uzytkownika
     * @param name imie uzytkownika
     * @param lastname nazwisko uzytkownika
     * @param password haslo uzytkownika
     * @param accNumber numer konta uzytkownika
     * @throws SQLException
     */
    public BankCustomer(int id, String email, String name, String lastname, String password, String accNumber) throws SQLException {
        setId(id);
        setDbController(Main.getDbController());
        setWallet(new Wallet());
        setEmail(email);
        setName(name);
        setLastname(lastname);
        setPassword(password);
        setAccNumber(accNumber);
        setIfLogOut(false);
        getLoanIfExist();
    }

    /**
     * Metoda implementujaca wplate, uaktualnia Wallet oraz tworzy wpis w bazie rejestrow
     * @param value kwota do wplaty na konto
     * @return TRUE jezeli operacja zostala zakonczona sukcesem
     * @throws SQLException
     */
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

    /**
     * Metoda implementujaca wyplate, uaktualnia Wallet oraz tworzy wpis w bazie rejestrow
     * @param value kwota do wyplaty na konto
     * @return TRUE jezeli operacja zostala zakonczona sukcesem
     * @throws SQLException
     */
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

    /**
     * Metoda implementujaca przelew, uaktualnia Wallet uzytkownika oraz odbiorcy, a takze tworzy wpis w bazie rejestrow
     * @param value kwota przelewu
     * @return TRUE jezeli operacja zostala zakonczona sukcesem
     * @throws SQLException
     */
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
            query = "INSERT INTO registers VALUES (NULL, '%d','przelew_wyslano_%s', '%s')".formatted(getId(), value.setScale(2, ROUND_DOWN).doubleValue(), localDate);
            getDbController().getStatement().executeUpdate(query);

            query = "SELECT idCustomer FROM customers WHERE accNumber='%s'".formatted(accountNumber);
            ResultSet resultSet = getDbController().getStatement().executeQuery(query);
            resultSet.next();
            int idRecipent = resultSet.getInt(1);

            query = "INSERT INTO registers VALUES (NULL, '%d','przelew_odebrano_%s', '%s')".formatted(idRecipent, value.setScale(2, ROUND_DOWN).doubleValue(), localDate);
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

    /**
     * Metoda udzielająca pożyczki klientowi. Zwiększa saldo w PLN portfela o wartość pożyczki,
     * dodaje wpis o pożyczce do bazy danych loans oraz rejestruje operację w bazie danych registers.
     *
     * @param value     Wartość pożyczki.
     * @param startdate  Data rozpoczęcia pożyczki.
     * @param endDate    Data zakończenia pożyczki.
     * @param percent   Procentowe oprocentowanie pożyczki.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    @Override
    public void getLoan(BigDecimal value, LocalDate startdate, LocalDate endDate, double percent) throws SQLException {
        try{

            double newValue = getWallet().getPln().setScale(2, ROUND_DOWN).add(value.setScale(2)).doubleValue();
            getWallet().setPln(newValue);

            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            query = "INSERT INTO loans VALUES (NULL, " + getId() + ", " + value + ", '%s', '%s', ".formatted(startdate, endDate) + percent + ")";
            getDbController().getStatement().executeUpdate(query);

            query = "INSERT INTO registers VALUES (NULL, '%d','udzielenie_pozyczki%s', '%s')".formatted(getId(), value.setScale(2, ROUND_DOWN).doubleValue(), startdate);
            getDbController().getStatement().executeUpdate(query);
            System.out.println("kk");
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

    /**
     * Metoda realizująca spłatę raty pożyczki. Redukuje saldo w PLN portfela o wartość raty.
     *
     * @param val Wartość raty do spłaty.
     * @return true, jeśli operacja została zrealizowana pomyślnie; false, jeśli brakuje środków na koncie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public boolean payInstallment(double val) throws SQLException {
        if(!isEnough(val)) return false;

        getWallet().setPln(getWallet().getPln().subtract(BigDecimal.valueOf(val)).doubleValue());
        return true;
    }

    /**
     * Metoda usuwająca pożyczkę klienta. Usuwa wpisy związane z pożyczką z bazy danych loans.
     *
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    public void delLoan() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            query = "DELETE FROM loans WHERE idCustomer=" + id;
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

    /**
     * Metoda umożliwiająca dokonanie wpłaty na konto. Redukuje saldo w PLN portfela o wartość wpłaty,
     * dodaje wpis o depozycie do bazy danych deposis oraz rejestruje operację w bazie danych registers.
     *
     * @param value     Wartość depozytu.
     * @param startDate Data rozpoczęcia depozytu.
     * @param endDate   Data zakończenia depozytu.
     * @param percent   Procentowe oprocentowanie depozytu.
     * @return true, jeśli operacja została zrealizowana pomyślnie; false, jeśli brakuje środków na koncie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    @Override
    public boolean makeDeposit(BigDecimal value, LocalDate startDate, LocalDate endDate, double percent) throws SQLException {
        if (!isEnough(value.doubleValue())) return false;

        try{
            double newValue = getWallet().getPln().setScale(2).subtract(value.setScale(2)).doubleValue();
            getWallet().setPln(newValue);

            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            query = "INSERT INTO deposis VALUES (NULL, " + getId() + ", " + value + ", '%s', '%s', ".formatted(startDate, endDate) + percent + ")";
            getDbController().getStatement().executeUpdate(query);

            query = "INSERT INTO registers VALUES (NULL, '%d','utworzenie_lokaty_%s', '%s')".formatted(getId(), value.setScale(2, ROUND_DOWN).doubleValue(), startDate);
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

    /**
     * Metoda umożliwiająca wymianę jednej waluty na inną. Redukuje saldo w PLN portfela o wartość wymienianej waluty,
     * dodaje odpowiednią kwotę do drugiej waluty w portfelu oraz rejestruje operację w bazie danych registers.
     *
     * @param firstValue   Wartość waluty do wymiany.
     * @param secondCurr   Druga waluta, na którą wymieniamy.
     * @param secondValue  Wartość wymienianej waluty w drugiej walucie.
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    @Override
    public void exchange(BigDecimal firstValue, String secondCurr, BigDecimal secondValue) throws SQLException {
        BigDecimal temp;

        if(getWallet().getPln().doubleValue() < firstValue.doubleValue()) return;

        getWallet().setPln(getWallet().getPln().doubleValue() - firstValue.doubleValue());

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

    /**
     * Metoda zwracająca obiekt portfela klienta.
     *
     * @return Obiekt portfela klienta.
     */
    public Wallet getWallet() {
        return this.wallet;
    }

    /**
     * Metoda prywatna ustawiająca portfel klienta.
     *
     * @param wallet Nowy portfel klienta.
     */
    private void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    /**
     * Metoda zwracająca kontroler bazy danych.
     *
     * @return Kontroler bazy danych.
     */
    public DbController getDbController() {
        return dbController;
    }

    /**
     * Metoda ustawiająca kontroler bazy danych.
     *
     * @param dbController Nowy kontroler bazy danych.
     */
    public void setDbController(DbController dbController) {
        this.dbController = dbController;
    }
    /**
     * Metoda ustawiająca identyfikator klienta.
     *
     * @param id Nowy identyfikator klienta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda zwracająca identyfikator klienta.
     *
     * @return Identyfikator klienta.
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda zwracająca numer konta klienta.
     *
     * @return Numer konta klienta.
     */
    public String getAccNumber() {
        return accNumber;
    }

    /**
     * Metoda ustawiająca numer konta klienta.
     *
     * @param accNumber Nowy numer konta klienta.
     */
    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    /**
     * Metoda sprawdzająca, czy klient jest wylogowany.
     *
     * @return true, jeśli klient jest wylogowany; false w przeciwnym przypadku.
     */
    public boolean isIfLogOut() {
        return ifLogOut;
    }

    /**
     * Metoda ustawiająca status wylogowania klienta.
     *
     * @param ifLogOut Nowy status wylogowania klienta.
     */
    public void setIfLogOut(boolean ifLogOut) {
        this.ifLogOut = ifLogOut;
    }

    /**
     * Metoda zwracająca aktywny element pożyczki klienta.
     *
     * @return Aktywny element pożyczki klienta.
     */
    public LoanElement getActiveLoan() {
        return activeLoan;
    }

    /**
     * Metoda ustawiająca aktywny element pożyczki klienta.
     *
     * @param activeLoan Nowy aktywny element pożyczki klienta.
     */
    public void setActiveLoan(LoanElement activeLoan) {
        this.activeLoan = activeLoan;
    }
    
    /**
     * Kapitalizacja odsetek lokaty, doladaowanie portfela oraz usuniecie lokaty z bazy
     * @param idDeposit id lokaty
     * @param sum kwota wplacona na lokate
     * @param percent procent lokaty
     * @throws SQLException
     */
    public void capitalization(IntegerProperty idDeposit, StringProperty sum, StringProperty percent) throws SQLException {
        try{
            BigDecimal bigPercent = new BigDecimal(String.valueOf(percent));
            bigPercent.add(BigDecimal.valueOf(1));

            BigDecimal newValue = getWallet().getPln().add(new BigDecimal(String.valueOf(sum)).multiply(bigPercent));
            getWallet().setPln(newValue.doubleValue());

            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;

            query = "DELETE FROM deposis WHERE idDeposis='%d'".formatted(idDeposit);
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

    /**
     * Metoda pomocnicza sprawdza czy uzytkownika posiada dostateczne zasoby aby zrealizowac operacje
     * @param value sprawdzana wartosc
     * @return TRUE jezeli uzytkownika dysponuje żądaną kwotą
     * @throws SQLException
     */
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

    /**
     * Metoda pomocnicza sprawdza czy istnieje uzytkownik o podanym numerze konta
     * @param accountNumber sprawdzany numer konta
     * @return TRUE jezeli istnieje
     * @throws SQLException
     */
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

    /**
     * Metoda wylogowywujaca uzytkonika z serwisu, tworzy wpis wbazie rejestrow.
     * Usuwa plik .bin bedacy podstawa do lokalnej serializacji obeiktu w wypadku nie wylogowania
     * @throws SQLException
     */
    public void logOut() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            LocalDate localDate = LocalDate.now();
            query = "INSERT INTO registers VALUES (NULL, '%d','wylogowano', '%s')".formatted(getId(), localDate);
            getDbController().getStatement().executeUpdate(query);
            setIfLogOut(true);

            File file = new File("serializedObjects.bin");

            if(file.isFile()) file.delete();

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

    /**
     * Metoda prywatna sprawdzająca istnienie aktywnej pożyczki klienta w bazie danych.
     * Jeżeli pożyczka istnieje, ustawia aktywny element pożyczki na odpowiedni obiekt klasy LoanElement.
     * W przeciwnym przypadku ustawia aktywny element pożyczki na null.
     *
     * @throws SQLException Wyjątek rzucany w przypadku problemów z bazą danych.
     */
    private void getLoanIfExist() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            query = "SELECT sum, startDate, endDate, percent FROM loans WHERE idCustomer=" + id;

            resultSet = getDbController().getStatement().executeQuery(query);
            LoanElement loan;
            String sum, startDate, endDate, percent;

            if(resultSet.next()){
                sum = resultSet.getString("sum");
                startDate = resultSet.getString("startDate");
                endDate = resultSet.getString("endDate");
                percent = resultSet.getString("percent");

                loan = new LoanElement(LocalDate.parse(startDate), LocalDate.parse(endDate), Double.parseDouble(sum), Double.parseDouble(percent));
                setActiveLoan(loan);
            }
            else{
                setActiveLoan(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            getDbController().getStatement().close();
            getDbController().getConnection().close();
        }
    }

}

