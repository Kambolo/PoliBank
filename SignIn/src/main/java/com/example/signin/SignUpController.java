package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

/**
 * Kontroler signUp.fxml
 */
public class SignUpController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private CheckBox female;
    @FXML
    private CheckBox male;
    @FXML
    private PasswordField passwdField1, passwdField2;
    @FXML
    private Button signUpButton;
    @FXML
    private Label conditionLabel;
    @FXML
    private TextField emailField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField lastnameField;
    private PasswordViewModel passwordViewModel = new PasswordViewModel();
    private DbController dbController;

    @FXML
    public void initialize(){
        passwdField1.textProperty().bindBidirectional(passwordViewModel.getPasswd1Property());
        passwdField2.textProperty().bindBidirectional(passwordViewModel.getPasswd2Property());
        conditionLabel.visibleProperty().bind(passwordViewModel.getConditionProperty());
        passwdField2.disableProperty().bind(passwordViewModel.getDisablePasswdField2Property());
        signUpButton.disableProperty().bind(passwordViewModel.getDisableSignUpProperty());
        emailField.textProperty().bindBidirectional(passwordViewModel.getEmailProperty());
        nameField.textProperty().bindBidirectional(passwordViewModel.getNameProperty());
        lastnameField.textProperty().bindBidirectional(passwordViewModel.getLastnameProperty());
        setDbController(Main.getDbController());
    }

    /**
     * Zmiana okna na ekran logowania
     * @param evt wcisniecie przycisku
     * @throws IOException
     */
    public void switchToSignIn(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Zmiana okna na ekran bledu przy rejestracji
     * @param evt wcisniecie przycisku
     * @throws IOException
     */
    public void switchToSignUpError(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(getClass().getResource("signUpError.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda inicjujaca rejestracje uzytkownika
     * @param evt wcisniecie przycisku
     * @throws IOException
     * @throws SQLException
     */
    public void signUp(ActionEvent evt) throws IOException, SQLException {
        /**
         * Anonimowe wywolanie metody signUp
         */
        SignUpOperation signUpOperation = new SignUpOperation() {
            @Override
            public void signUp() throws SQLException, IOException {
                String name = nameField.getText();
                String lastname = lastnameField.getText();
                String email = emailField.getText();
                String password = passwdField1.getText();

                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
                    getDbController().setStatement(getDbController().getConnection().createStatement());

                    String query;
                    ResultSet resultSet;

                    query = "SELECT COUNT(email) FROM customers WHERE email='%s'".formatted(email);
                    resultSet = getDbController().getStatement().executeQuery(query);
                    resultSet.next();
                    if(resultSet.getInt(1) == 0) {

                        Random generator = new Random();
                        String accNr = "";
                        boolean condition = true;

                        while(condition){
                            accNr = "";
                            for(int i = 0; i < 10; i++){
                                accNr += generator.nextInt(9);
                            }

                            query = "SELECT COUNT(accNumber) FROM customers WHERE accNumber='%s'".formatted(accNr);
                            resultSet = getDbController().getStatement().executeQuery(query);
                            resultSet.next();

                            if(resultSet.getInt(1) == 0) condition = false;
                        }

                        query = "INSERT INTO customers VALUES (NULL, '%s', '%s', '%s', '%s', '%s')".formatted(name, lastname, email, password, accNr);
                        getDbController().getStatement().executeUpdate(query);

                        query = "SELECT idCustomer FROM customers WHERE email='%s' AND password='%s'".formatted(email, password);
                        resultSet = getDbController().getStatement().executeQuery(query);
                        resultSet.next();
                        int id = resultSet.getInt(1);
                        LocalDate localDate = LocalDate.now();

                        query = "INSERT INTO wallet VALUES ('%d', 0, 0, 0, 0)".formatted(id);
                        getDbController().getStatement().executeUpdate(query);

                        query = "INSERT INTO registers VALUES (NULL, '%d', 'zarejestrowano', '%s')".formatted(id, localDate);
                        getDbController().getStatement().executeUpdate(query);

                        root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
                        scene = new Scene(root);
                        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else throw new IllegalArgumentException("emails locked");

                } catch (IllegalArgumentException e){
                    switchToSignUpError(evt);
                } catch(SQLException e){
                    System.out.println(e);
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                } finally {
                    getDbController().getStatement().close();
                    getDbController().getConnection().close();
                }
            }
        };

        signUpOperation.signUp();
    }

    public boolean femaleChecked(ActionEvent evt){
        if(female.isSelected()){
            male.setSelected(false);
            return true;
        }
        male.setSelected(true);
        return false;
    }

    public boolean maleChecked(ActionEvent evt){
        if(male.isSelected()){
            female.setSelected(false);
            return true;
        }
        female.setSelected(true);
        return false;
    }

    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}
}
