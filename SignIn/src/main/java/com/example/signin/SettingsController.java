package com.example.signin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Klasa implementujaca modul ustawien
 */
public class SettingsController {
    @FXML
    private Label incorrectPassword;
    @FXML
    private Label conditionLabel;
    @FXML
    private TextField oldPassword;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newPassword2;
    @FXML
    private Button confirmButton;
    @FXML
    private Label resultLabel;
    private NewPasswordViewModel newPasswordViewModel = new NewPasswordViewModel();
    private BooleanProperty incorrectPasswordProperty = new SimpleBooleanProperty();
    private BooleanProperty resultLabelProperty = new SimpleBooleanProperty();
    private BankCustomer bankCustomer;
    private DbController dbController;

    @FXML
    public void initialize(){
        setBankCustomer(Main.getBankCustomer());
        setDbController(Main.getDbController());

        oldPassword.textProperty().bindBidirectional(newPasswordViewModel.getOldPasswdProperty());
        newPassword.textProperty().bindBidirectional(newPasswordViewModel.getNewPasswd1Property());
        newPassword2.textProperty().bindBidirectional(newPasswordViewModel.getNewPasswd2Property());
        newPassword2.disableProperty().bind(newPasswordViewModel.getDisableNewPassword2Property());
        conditionLabel.visibleProperty().bind(newPasswordViewModel.getConditionProperty());
        confirmButton.disableProperty().bind(newPasswordViewModel.getDisableConfirmButtonProperty());
        incorrectPassword.visibleProperty().bind(incorrectPasswordProperty);
        resultLabel.visibleProperty().bind(resultLabelProperty);

        incorrectPasswordProperty.set(false);
    }

    /**
     * Metoda inicjujaca zmiane hasla
     * @param evt wcisniecie przycisku
     * @throws SQLException
     */
    public void changePassword(ActionEvent evt) throws SQLException {
        if(!getBankCustomer().getPassword().equals(oldPassword.getText())){
            incorrectPasswordProperty.set(true);
            return;
        }

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            getBankCustomer().setPassword(newPassword.getText());

            String query;
            query = "UPDATE customers SET password='%s' WHERE idCustomer='%d'".formatted(getBankCustomer().getPassword(), getBankCustomer().getId());
            getDbController().getStatement().executeUpdate(query);

            query = "INSERT INTO registers VALUES (NULL, '%d','zmiana_hasla', '%s')".formatted(getBankCustomer().getId(), LocalDate.now());
            getDbController().getStatement().executeUpdate(query);

            resultLabel.setText("Operacja zakonczona sukcesem!");
            return;


        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        finally {
            resultLabelProperty.set(true);
            getDbController().getStatement().close();
            getDbController().getConnection().close();
        }

        resultLabel.setText("Operacja zakonczona niepowodzeniem");
    }
    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}
}
