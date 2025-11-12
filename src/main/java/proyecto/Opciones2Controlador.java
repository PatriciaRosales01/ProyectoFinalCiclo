/**
 * Clase: Opciones2Controlador
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana opciones2.fxml,
 * encargada de mostrar los niveles de dificultad disponibles y
 * permite al usuario seleccionar uno antes de la actividad. También
 * inlcuye a mayores la opción para ver los resultados
 * */

package proyecto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Opciones2Controlador {

    /**Maneja la selección del modo difícil y abre la ventana correspondiente*/
    @FXML
    void nivelDificil(ActionEvent event) {
        abrirVentana("/nivelDificil/nivelDificil.fxml", "Nivel difícil", event);
    }

    /**Maneja la selección del modo fácil y abre la ventana correspondiente*/
    @FXML
    void nivelFacil(ActionEvent event) {
        abrirVentana("/nivelFacil/nivelFacil.fxml", "Nivel fácil", event);
    }

    /**Maneja la selección del modo medio y abre la ventana correspondiente*/
    @FXML
    void nivelMedio(ActionEvent event) {
        abrirVentana("/nivelMedio/nivelMedio.fxml", "Nivel medio", event);
    }

    /**Abre la ventana de resultados mostrando los aciertos y los fallos*/
    @FXML
    void verResultados(ActionEvent event) {
        abrirVentana("/resultado.fxml", "Resultados", event);
    }

    /**Método auxiliar para cargar un nuevo FXMl en la ventana actual*/
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
