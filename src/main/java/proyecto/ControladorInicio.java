/**
 * Clase: ControladorInicio
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana inicio.fxml,
 * encargada de mostrar la pantalla principal del programa, desde
 * la que el usuario puede acceder a los diferentes niveles y
 * actividades
 * */

package proyecto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorInicio {

    @FXML
    private TextField nombreUsuario;

    @FXML
    private void aceptar(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/dificultad.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Opciones");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
