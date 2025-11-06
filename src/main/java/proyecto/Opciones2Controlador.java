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

    @FXML
    void nivelDificil(ActionEvent event) {
        abrirVentana("/nivelDificil/nivelDificil.fxml", "Nivel difícil", event);
    }

    @FXML
    void nivelFacil(ActionEvent event) {
        abrirVentana("/nivelFacil/nivelFacil.fxml", "Nivel fácil", event);
    }

    @FXML
    void nivelMedio(ActionEvent event) {
        abrirVentana("/nivelMedio/nivelMedio.fxml", "Nivel medio", event);
    }

    @FXML
    void verResultados(ActionEvent event) {
        abrirVentana("/resultado.fxml", "Resultados", event);
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
