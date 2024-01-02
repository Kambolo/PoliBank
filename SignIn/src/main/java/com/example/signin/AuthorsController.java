package com.example.signin;

import javafx.event.ActionEvent;

import java.net.URI;
import java.awt.Desktop;

/**
 * Kontroler authors.fxml
 */
public class AuthorsController {

    /**
     * Metoda otwierajaca link do profilu na Github Kamila
     * @param evt wcisniecie przycisku
     */
    public void kamilGithub(ActionEvent evt){
        try {

            Desktop d = Desktop.getDesktop();
            d.browse(new URI("https://github.com/Kambolo"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda otwierajaca link do profilu na Github Kacpra
     * @param evt wcisniecie przycisku
     */
    public void kacperGithub(ActionEvent evt){
        try {

            Desktop d = Desktop.getDesktop();
            d.browse(new URI("https://github.com/KacperKiec"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
