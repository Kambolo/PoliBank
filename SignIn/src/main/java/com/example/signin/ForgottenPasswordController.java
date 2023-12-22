package com.example.signin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.signin.Main.getDbController;

/**
    Klasa kontrolera sceny forgottenPassword
 */
public class ForgottenPasswordController {
    @FXML
    private Label message;

    @FXML
    private TextField email;

    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Funkcja wywołująca się przy przełączeniu się na forgottenPassword
     */
    public void initialize(){
        message.setText("");
    }

    /**
     * Funkcja sprawdzjaca czy email jest poprawny oraz wysylajaca maila z przypomnieniem hasla
     * @param evt kliknęcie na przycisk
     */
    public void checkEmail(ActionEvent evt) throws SQLException {
        String password = getPassword(email.getText());
        if(password != null){
            message.setTextFill(Color.GREEN);
            message.setText("Na twój adres email został wysłany mail.");
            sendMail(email.getText(), password);
        }
        else{
            message.setTextFill(Color.RED);
            message.setText("Podany adres nie istnieje w naszej bazie danych.");
        }
    }

    /**
     * Funkcja zwraca hasło połączone z danym emailem
     * @param email sprawdzany email
     * @throws IOException
     * @return hasło połączone z danym emilem, null jesli taki email nie istnieje w bazie
     */
    private String getPassword(String email) throws SQLException {
        String password = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            getDbController().setConnection(DriverManager.getConnection(getDbController().getUrl(), getDbController().getUsername(), getDbController().getPassword()));
            getDbController().setStatement(getDbController().getConnection().createStatement());

            String query;
            ResultSet resultSet;

            query = "SELECT password FROM customers WHERE email='%s'".formatted(email);
            resultSet = getDbController().getStatement().executeQuery(query);

            if (resultSet.next()) {
                password = resultSet.getString("password");
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            getDbController().getStatement().close();
            getDbController().getConnection().close();
        }

        return password;
    }

    private void sendMail(String email, String password){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python",
                    "src/main/resources/com/example/signin/emailSender.py", email, password);

            /*
                Ta czesc kodu zostala dodana aby błędy które zostay wytworzone w pythonowym skrypcie
                mogły zostac wyswietlone w terminalu naszej aplikacji
             */

            // Redirect error stream to the output stream
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read the output (including error messages) to help diagnose issues
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the process to finish
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funkcja zmieniająca scene na strone logowania
     * @param evt kliknięcie na przycisk
     * @throws IOException
     */
    public void switchToSignIn(ActionEvent evt) throws IOException {
        root = FXMLLoader.load(this.getClass().getResource("signIn.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)evt.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
