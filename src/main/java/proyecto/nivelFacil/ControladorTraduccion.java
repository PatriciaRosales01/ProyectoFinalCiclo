/**
 * Clase: ControladorTraduccion
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana traduccion.fxml,
 * encargada de mostrar una palabra en un idioma y el usuario tiene
 * que escribir su traducción en inglés y en francés
 * */

package proyecto.nivelFacil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.DatabaseService;
import proyecto.Palabra;
import proyecto.Resultados;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControladorTraduccion {

    @FXML
    private Label palabraEspañol;

    @FXML
    private TextField traduccionIngles;

    @FXML
    private TextField traduccionFrances;

    private List<Palabra> listaPalabras = new ArrayList<>();
    private Palabra palabraActual;
    private Random aleatorio = new Random();

    /**Inicializa la ventana cargando las palabras desde la base de datos*/
    @FXML
    public void initialize() {
        cargarPalabrasDesdeBD();
        nuevaPalabra();
    }

    /**Carga las palabras y sus traducciones desde la tabla traducir*/
    private void cargarPalabrasDesdeBD() {
        DatabaseService db = new DatabaseService();
        listaPalabras = new ArrayList<>();

        String sql = "SELECT palabraEspañol, traduccionIngles, traduccionFrances FROM traducir";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Palabra p = new Palabra();
                p.setPalabra(rs.getString("palabraEspañol"));
                p.setTraduccion(rs.getString("traduccionIngles") + "," + rs.getString("traduccionFrances"));
                listaPalabras.add(p);
            }

            System.out.println("Se cargaron " + listaPalabras.size() + " palabras desde traducir.");

        } catch (SQLException e) {
            System.err.println("Error al cargar palabras: " + e.getMessage());
        }
    }

    /**Selecciona una palabra aleatoria. Desactiva la entrada cuando se acaban las palabras*/
    private void nuevaPalabra() {
        if (!listaPalabras.isEmpty()) {
            int index = aleatorio.nextInt(listaPalabras.size());
            palabraActual = listaPalabras.remove(index);

            palabraEspañol.setText(palabraActual.getPalabra());
            traduccionIngles.clear();
            traduccionFrances.clear();
        } else {
            palabraEspañol.setText("Has terminado todas las palabras");
            traduccionIngles.setDisable(true);
            traduccionFrances.setDisable(true);
        }
    }

    /**Combinación de los métodos aceptar y comprobar*/
    @FXML
    void aceptarComprobar(ActionEvent event) {
        comprobar();
        aceptar(event);
    }

    /**Guarda los resultados y muestra la ventana del menú*/
    @FXML
    void aceptar(ActionEvent event) {
        new DatabaseService().guardarResultados();

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/opciones2.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Menú");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Comprueba si las traducciones introducidas coinciden con la correcta.
     * Actualiza los aciertos y los fallos y carga la siguiente palabra*/
    @FXML
    void comprobar() {
        String[] traducciones = palabraActual.getTraduccion().split(",");
        String palabraIngles = traducciones[0].trim();
        String palabraFrances = traducciones[1].trim();

        int aciertosE = 0, fallosE = 0;
        int aciertosI = 0, fallosI = 0;
        int aciertosF = 0, fallosF = 0;

        if (traduccionIngles.getText().trim().equalsIgnoreCase(palabraIngles)) {
            aciertosI = 1;
        } else {
            fallosI = 1;
        }

        if (traduccionFrances.getText().trim().equalsIgnoreCase(palabraFrances)) {
            aciertosF = 1;
        } else {
            fallosF = 1;
        }

        Resultados.añadirResultados(aciertosE, fallosE, aciertosI, fallosI, aciertosF, fallosF);

        nuevaPalabra();
    }


}
