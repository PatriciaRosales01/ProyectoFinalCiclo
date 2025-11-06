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
    @FXML
    void seleccionar(ActionEvent event) {
        System.out.println("Seleccionar opción correcta");
        abrirVentana("/nivelFacil/escoger.fxml", "Nivel fácil", event);
    }

    @FXML
    void traducir(ActionEvent event) {
        System.out.println("Traducción directa");
        abrirVentana("/nivelFacil/traduccion.fxml", "Nivel fácil", event);
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
