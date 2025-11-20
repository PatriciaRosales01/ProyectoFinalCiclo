/**
 * Clase: Main
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Clase principal que inicia JavaFx, carga la ventana
 * principal y establece el flujo inicial de la interfaz
 * */

package proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**Inicializa la base de datos, carga los datos y muestra la ventana inicial*/
    @Override
    public void start(Stage stage) throws Exception {
        DatabaseService db = new DatabaseService();
        db.inicializarBaseDeDatos();
        db.cargarResultados();

        Resultados.resetActividad();

        Parent root = FXMLLoader.load(getClass().getResource("/inicio.fxml"));
        stage.setTitle("Aprende Idiomas");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}