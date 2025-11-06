package proyecto.nivelDificil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorNivelDificil {
    @FXML
    void columnas(ActionEvent event) {
        System.out.println("Unir columnas");
        abrirVentana("/nivelDificil/columnas.fxml", "Nivel dificil", event);
    }

    @FXML
    void ordenar(ActionEvent event) {
        System.out.println("Ordenar palabras");
        abrirVentana("/nivelDificil/ordenar.fxml", "Nivel dificil", event);
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
