package com.example.signin;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import static com.example.signin.Main.getDbController;

public class Currencies {
    private ArrayList<Double> currenciesList;
    private double eurBuy;
    private double eurSell;
    private double gbpBuy;
    private double gbpSell;
    private double usdBuy;
    private double usdSell;
    Currencies(){
        setCurrenciesList(new ArrayList<Double>(6));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            query = "SELECT eur FROM currencies";
            resultSet = getDbController().getStatement().executeQuery(query);
            int temp=0;

            while(resultSet.next() && temp<2){
                getCurrenciesList().addLast(resultSet.getDouble("eur"));
                temp++;
            }
            temp=0;

            query = "SELECT gbp FROM currencies";
            resultSet = getDbController().getStatement().executeQuery(query);

            while(resultSet.next() && temp<2){
                getCurrenciesList().addLast(resultSet.getDouble("gbp"));
                temp++;
            }
            temp=0;

            query = "SELECT usd FROM currencies";
            resultSet = getDbController().getStatement().executeQuery(query);

            while(resultSet.next() && temp<2){
                getCurrenciesList().addLast(resultSet.getDouble("usd"));
                temp++;
            }

            eurSell = currenciesList.get(0);
            eurBuy = currenciesList.get(1);
            gbpSell = currenciesList.get(2);
            gbpBuy = currenciesList.get(3);
            usdSell = currenciesList.get(4);
            usdBuy = currenciesList.get(5);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    private ArrayList<Double> getCurrenciesList() {
        return currenciesList;
    }

    private void setCurrenciesList(ArrayList<Double> currenciesList) {
        this.currenciesList = currenciesList;
    }

    public double getEurBuy() {
        return eurBuy;
    }

    public double getEurSell() {
        return eurSell;
    }

    public double getGbpBuy() {
        return gbpBuy;
    }

    public double getGbpSell() {
        return gbpSell;
    }

    public double getUsdBuy() {
        return usdBuy;
    }

    public double getUsdSell() {
        return usdSell;
    }
}
