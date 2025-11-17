/**
 * Clase: ControladorNivelFacil
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana nivelFacil.fxml,
 * encargada de mostrar las actividades que hay en ese nivel para
 * que el usuario escoja una de ellas
 * */

package proyecto.nivelFacil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorNivelFacil {
    /**Abre la actividad 'Seleccionar la opción correcta'*/
    @FXML
    void seleccionar(ActionEvent event) {
        System.out.println("Seleccionar opción correcta");
        abrirVentana("/nivelFacil/escoger.fxml", "Nivel fácil", event);
    }

    /**Abre la actividad 'Traducción directa'*/
    @FXML
    void traducir(ActionEvent event) {
        System.out.println("Traducción directa");
        abrirVentana("/nivelFacil/traduccion.fxml", "Nivel fácil", event);
    }

    /**Carga y muestra una nueva ventana según el FXML indicado*/
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
