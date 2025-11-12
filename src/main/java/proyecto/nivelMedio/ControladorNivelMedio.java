/**
 * Clase: ControladorNivelMedio
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana nivelMedio.fxml,
 * encargada de mostrar las actividades que hay en ese nivel para
 * que el usuario escoja una de ellas
 * */

package proyecto.nivelMedio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorNivelMedio {
    @FXML
    void adivinar(ActionEvent event) {
        System.out.println("Adivinar palabra");
        abrirVentana("/nivelMedio/adivinar.fxml", "Nivel medio", event);
    }

    @FXML
    void espacio(ActionEvent event) {
        System.out.println("Completar espacio en blanco");
        abrirVentana("/nivelMedio/espacio.fxml", "Nivel medio", event);
    }

    private void abrirVentana(String fxml, String titulo, ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
