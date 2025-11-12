/**
 * Clase: ControladorAdivinar
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana adivinar.fxml,
 * encargada de mostrar una pista y que el usuario adivine la palabra
 * correcta en español, inglés y francés
 * */

package proyecto.nivelMedio;

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

public class ControladorAdivinar {

    @FXML
    private Label frase;

    @FXML
    private TextField español;

    @FXML
    private TextField frances;

    @FXML
    private TextField ingles;

    private List<Palabra> listaPalabras = new ArrayList<>();
    private Palabra palabraActual;
    private Random aleatorio = new Random();

    @FXML
    public void initialize() {
        cargarPalabrasDesdeBD();
        nuevaPalabra();
    }

    private void cargarPalabrasDesdeBD() {
        DatabaseService db = new DatabaseService();
        listaPalabras = new ArrayList<>();

        String sql = "SELECT pista, respuesta_es, respuesta_en, respuesta_fr FROM adivinar";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Palabra p = new Palabra();
                p.setPista(rs.getString("pista"));
                p.setTraduccion(rs.getString("respuesta_es") + "," +
                        rs.getString("respuesta_en") + "," +
                        rs.getString("respuesta_fr"));
                listaPalabras.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void nuevaPalabra() {
        if (!listaPalabras.isEmpty()) {
            int index = aleatorio.nextInt(listaPalabras.size());
            palabraActual = listaPalabras.remove(index);

            frase.setText(palabraActual.getPista());
            español.clear();
            ingles.clear();
            frances.clear();
        } else {
            frase.setText("Has terminado todas las palabras.");
            español.setDisable(true);
            ingles.setDisable(true);
            frances.setDisable(true);
        }
    }

    @FXML
    void aceptarComprobar(ActionEvent event) {
        comprobar();
        aceptar(event);
    }

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


    @FXML
    void comprobar() {
        String[] respuestas = palabraActual.getTraduccion().split(",");
        String españolCorrecto = respuestas[0].trim();
        String inglesCorrecto = respuestas[1].trim();
        String francesCorrecto = respuestas[2].trim();

        if (español.getText().trim().equalsIgnoreCase(españolCorrecto)) {
            Resultados.setAciertosEspañol(Resultados.getAciertosEspañol() + 1);
        } else {
            Resultados.setFallosEspañol(Resultados.getFallosEspañol() + 1);
        }

        if (ingles.getText().trim().equalsIgnoreCase(inglesCorrecto)) {
            Resultados.setAciertosIngles(Resultados.getAciertosIngles() + 1);
        } else {
            Resultados.setFallosIngles(Resultados.getFallosIngles() + 1);
        }

        if (frances.getText().trim().equalsIgnoreCase(francesCorrecto)) {
            Resultados.setAciertosFrances(Resultados.getAciertosFrances() + 1);
        } else {
            Resultados.setFallosFrances(Resultados.getFallosFrances() + 1);
        }

        nuevaPalabra();
    }
}
