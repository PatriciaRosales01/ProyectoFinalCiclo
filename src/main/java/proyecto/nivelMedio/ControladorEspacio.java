/**
 * Clase: ControladorEspacio
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana espacio.fxml,
 * encargada de mostrar frases con un espacio en blanco que el
 * usuario tiene que completar de forma correcta según el idioma
 * */

package proyecto.nivelMedio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proyecto.DatabaseService;
import proyecto.Resultados;

import java.io.IOException;
import java.util.*;

public class ControladorEspacio {

    @FXML private Label lblFrase;
    @FXML private TextField txtRespuesta;
    @FXML private Button btnComprobar;
    @FXML private Button btnOtraFrase;
    @FXML private Label lblResultado;

    private final DatabaseService databaseService = new DatabaseService();
    private List<Frase> frases;
    private Frase fraseActual;

    private final List<Boolean> resultados = new ArrayList<>();

    public static class Frase {
        String texto;
        String respuesta;
        String idioma;

        public Frase(String texto, String respuesta, String idioma) {
            this.texto = texto;
            this.respuesta = respuesta;
            this.idioma = idioma;
        }
    }

    /**Inicializa la ventana cargando las palabras desde la base de datos*/
    @FXML
    public void initialize() {
        frases = databaseService.obtenerFrasesCompletar();

        if (frases == null || frases.isEmpty()) {
            lblFrase.setText("No hay frases disponibles.");
            btnComprobar.setDisable(true);
            btnOtraFrase.setDisable(true);
            return;
        }

        mostrarFraseAleatoria();

        btnComprobar.setOnAction(this::aceptarComprobar);
        btnOtraFrase.setOnAction(event -> mostrarNuevaFrase());
    }

    /**Muestra una frase aleatoria disponible y limpia el campo de respuesta*/
    private void mostrarFraseAleatoria() {
        Random random = new Random();
        fraseActual = frases.get(random.nextInt(frases.size()));
        lblFrase.setText(fraseActual.texto);
        txtRespuesta.clear();
    }

    /**Guarda si la frase anterior fue correcta o no y muestra una nueva*/
    private void mostrarNuevaFrase() {
        if (fraseActual != null) {
            String respuestaUsuario = txtRespuesta.getText().trim().toLowerCase();
            boolean esCorrecto = respuestaUsuario.equals(fraseActual.respuesta.toLowerCase());
            resultados.add(esCorrecto);
        }

        frases.remove(fraseActual);

        if (frases.isEmpty()) {
            lblFrase.setText("¡Actividad terminada!");
            txtRespuesta.setDisable(true);
            btnOtraFrase.setDisable(true);
            btnComprobar.setDisable(true);
            return;
        }

        mostrarFraseAleatoria();
    }

    /**Combinación de los métodos aceptar y comprobar*/
    @FXML
    void aceptarComprobar(ActionEvent event) {
        comprobar();
        aceptar(event);
    }

    /**Guarda los resultados en la base de datos y vuelve al menú*/
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

    /**Comprueba si la respuesta es correcta y actualiza los contadores*/
    @FXML
    void comprobar() {
        if (fraseActual == null) return;

        String respuestaUsuario = txtRespuesta.getText().trim();
        boolean correcto = respuestaUsuario.equalsIgnoreCase(fraseActual.respuesta);

        switch (fraseActual.idioma.toLowerCase()) {
            case "es":
                if (correcto) Resultados.setAciertosEspañol(Resultados.getAciertosEspañol() + 1);
                else Resultados.setFallosEspañol(Resultados.getFallosEspañol() + 1);
                break;

            case "en":
                if (correcto) Resultados.setAciertosIngles(Resultados.getAciertosIngles() + 1);
                else Resultados.setFallosIngles(Resultados.getFallosIngles() + 1);
                break;

            case "fr":
                if (correcto) Resultados.setAciertosFrances(Resultados.getAciertosFrances() + 1);
                else Resultados.setFallosFrances(Resultados.getFallosFrances() + 1);
                break;
        }
    }

}
