/**
 * Clase: ControladorDificultad
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana dificultad.fxml,
 * encargada de mostrar los niveles de dificultad disponibles y
 * permite al usuario seleccionar uno antes de la actividad
 * */

package proyecto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorDificultad {

    @FXML private ToggleButton facil;
    @FXML private ToggleButton medio;
    @FXML private ToggleButton dificil;

    private ToggleGroup grupoDificultad;

    @FXML
    public void initialize() {
        grupoDificultad = new ToggleGroup();

        facil.setToggleGroup(grupoDificultad);
        medio.setToggleGroup(grupoDificultad);
        dificil.setToggleGroup(grupoDificultad);
    }

    @FXML
    private void facil(ActionEvent event) {
        System.out.println("Modo fácil seleccionado");
        abrirVentana("/nivelFacil/nivelFacil.fxml", "Nivel fácil", event);
    }

    @FXML
    private void medio(ActionEvent event) {
        System.out.println("Modo medio seleccionado");
        abrirVentana("/nivelMedio/nivelMedio.fxml", "Nivel medio", event);
    }

    @FXML
    private void dificil(ActionEvent event) {
        System.out.println("Modo difícil seleccionado");
        abrirVentana("/nivelDificil/nivelDificil.fxml", "Nivel difícil", event);
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
