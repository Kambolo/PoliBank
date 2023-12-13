package com.example.signin;

import java.sql.SQLException;

public interface SignInOperation {
    void signIn(String username, String password) throws ClassNotFoundException, SQLException;
}
