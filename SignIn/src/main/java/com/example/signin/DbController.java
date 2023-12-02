package com.example.signin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class DbController {
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private Statement statement;

    public DbController(){
        this.url = "jdbc:mysql://localhost:3306/bankdb";
        this.username = "root";
        this.password = "";
    }
    public String getUrl() {return url;}

    public void setUrl(String url) {this.url = url;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Connection getConnection() {return connection;}

    public void setConnection(Connection connection) {this.connection = connection;}
    public Statement getStatement() {return statement;}
    public void setStatement(Statement statement) {this.statement = statement;}
}
