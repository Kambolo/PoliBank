package com.example.signin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler desktop.fxml
 */
public class DesktopController {
    @FXML
    private Label personalDataLabel;
    @FXML
    private Label accNrLabel;
    @FXML
    private ChoiceBox<String> currencyID;
    @FXML
    private Label valueLabel;
    private StringProperty valueProperty = new SimpleStringProperty();
    private BankCustomer bankCustomer;
    private DbController dbController;
    private final ObservableList<String> choices = FXCollections.observableArrayList("PLN", "EUR", "GBP", "USD");
    @FXML
    private TableView<DepositElement> depositsTable;
    @FXML
    private TableColumn<DepositElement, String> startDateColumn;
    @FXML
    private TableColumn<DepositElement, String> endDateColumn;
    @FXML
    private TableColumn<DepositElement, String> percentColumn;
    @FXML
    private TableColumn<DepositElement, String> sumColumn;
    private ObservableList<DepositElement> elements;

    @FXML
    public void initialize(){
        setDbController(Main.getDbController());
        setBankCustomer(Main.getBankCustomer());
        personalDataLabel.setText(getBankCustomer().getName() + " " + getBankCustomer().getLastname());
        accNrLabel.setText(getBankCustomer().getAccNumber());
        valueLabel.textProperty().bind(valueProperty);

        currencyID.setValue(choices.get(0));
        currencyID.setItems(choices);
        valueProperty.set(String.valueOf(getBankCustomer().getWallet().getPln()));

        currencyID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                switch (currencyID.getValue()) {
                    case "PLN" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getPln()));
                    case "EUR" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getEur()));
                    case "GBP" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getGbp()));
                    case "USD" -> valueProperty.set(String.valueOf(getBankCustomer().getWallet().getUsd()));
                    default -> valueLabel.setText("???");
                };
            }
        });

        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        depositsTable.setItems(elements);
        startDateColumn.setCellValueFactory(cellData->cellData.getValue().getStartDate());
        endDateColumn.setCellValueFactory(cellData->cellData.getValue().getEndDate());
        percentColumn.setCellValueFactory(cellData->cellData.getValue().getPercent());
        sumColumn.setCellValueFactory(cellData->cellData.getValue().getSum());

        try {
            checkDepositsExpire();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metoda pomocnicza pobierajaca z bazy liste lokat uzytkownika
     * @throws SQLException
     */
    private void init() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            query = "SELECT idDeposis, sum, startDate, endDate, percent FROM deposis WHERE idCustomer=" + getBankCustomer().getId();

            resultSet = getDbController().getStatement().executeQuery(query);

            List<DepositElement> list = new ArrayList<>();
            int id;
            String sum, startDate, endDate, percent;

            while(resultSet.next()){
                id = resultSet.getInt("idDeposis");
                sum = resultSet.getString("sum");
                startDate = resultSet.getString("startDate");
                endDate = resultSet.getString("endDate");
                percent = resultSet.getString("percent");
                list.add(new DepositElement(id, sum, startDate, endDate, percent));
            }

            elements = FXCollections.observableArrayList(list);

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
     * Metoda sprawdzajaca, ktore z lokat uzytkownika sie zakonczyly
     */
    private void checkDepositsExpire() throws SQLException {
        String data;

        for(DepositElement e : elements){
            data = e.getEndDate().get();
            if(LocalDate.now().isAfter(LocalDate.parse(data))) getBankCustomer().capitalization(e.getId(), e.getSum(), e.getPercent());
        }
    }

    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}
}
