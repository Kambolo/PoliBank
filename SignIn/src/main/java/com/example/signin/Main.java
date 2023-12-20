package com.example.signin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

public class Main extends Application {
    private static DbController dbController;
    private static BankCustomer bankCustomer;

    @Override
    public void start(Stage stage) throws IOException {
        setDbController(new DbController());

        /**
         * Przywrocenie stanu sprzed zamkniecia aplikacji w przypadku nie wylogowanego uzytkownika przy pomocy deserializacji obiektu.
         * W przypadku gdy plik .bin z zapisanym obiektem nie istnieje lub nie mozna go otworzyc nastepuje przekierowanie do menu logowania.
         */
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("serializedObjects.bin"))){
            setBankCustomer((BankCustomer) inputStream.readObject());
            getBankCustomer().setDbController(getDbController());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

        } finally {
            stage.setTitle("PoliBank");
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(e -> serializeObject());
        }
    }

    public static void main(String[] args) {launch();}

    public static DbController getDbController() {return dbController;}
    public void setDbController(DbController dbController) {Main.dbController = dbController;}
    public static BankCustomer getBankCustomer() {return bankCustomer;}
    public static void setBankCustomer(BankCustomer bankCustomer) {Main.bankCustomer = bankCustomer;}

    /**
     * Metoda wywolywana podczas zamkniecia okna aplikacji.
     * Serializuje obiekt w celu jego ponownego wykorzystania przy ponownym uruchomieniu.
     */
    private void serializeObject(){
        if(getBankCustomer() != null && getBankCustomer().isIfLogOut() == false){
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("serializedObjects.bin"))) {
                outputStream.writeObject(getBankCustomer());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}