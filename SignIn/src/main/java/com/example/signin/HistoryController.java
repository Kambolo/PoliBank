package com.example.signin;

import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HistoryController {
    @FXML
    private TableView<HistoryElement> operationsTable;
    @FXML
    private TableColumn<HistoryElement, String> dateColumn;
    @FXML
    private TableColumn<HistoryElement, String> operationColumn;
    private ObservableList<HistoryElement> elements;
    private DbController dbController;
    private BankCustomer  bankCustomer;

    @FXML
    public void initialize(){
        setDbController(Main.getDbController());
        setBankCustomer(Main.getBankCustomer());

        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        operationsTable.setItems(elements);
        dateColumn.setCellValueFactory(cellData->cellData.getValue().getDate());
        operationColumn.setCellValueFactory(cellData->cellData.getValue().getOperation());
    }
    public void init() throws SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            query = "SELECT operation, date FROM registers WHERE idCustomer=" + getBankCustomer().getId() + " AND operation LIKE 'wplata%' OR operation LIKE 'wyplata%' OR operation LIKE 'przelew%' OR operation LIKE 'wymiana%'";

            resultSet = getDbController().getStatement().executeQuery(query);

            List<HistoryElement> list = new ArrayList<>();
            String operation, date;

            while(resultSet.next()){
                operation = resultSet.getString("operation");
                date = resultSet.getString("date");
                list.add(new HistoryElement(operation, date));
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
    public DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {this.dbController = dbController;}
    public BankCustomer getBankCustomer() {return bankCustomer;}
    public void setBankCustomer(BankCustomer bankCustomer) {this.bankCustomer = bankCustomer;}
}
