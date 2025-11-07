package proyecto.nivelFacil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proyecto.DatabaseService;
import proyecto.Palabra;
import proyecto.Resultados;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ControladorEscoger {

    @FXML
    private ComboBox<String> comboBoxFrances;

    @FXML
    private ComboBox<String> comboBoxIngles;

    @FXML
    private Label palabra;

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
        listaPalabras.clear();

        String sql = "SELECT palabra_es, traduccion_en, traduccion_fr FROM escoger";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Palabra p = new Palabra();
                p.setPalabra(rs.getString("palabra_es"));
                p.setTraduccion(rs.getString("traduccion_en") + "," + rs.getString("traduccion_fr"));
                listaPalabras.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void nuevaPalabra() {
        if (listaPalabras.isEmpty()) {
            palabra.setText("Has terminado todas las palabras");
            comboBoxIngles.setDisable(true);
            comboBoxFrances.setDisable(true);
            return;
        }

        int index = aleatorio.nextInt(listaPalabras.size());
        palabraActual = listaPalabras.remove(index);

        palabra.setText(palabraActual.getPalabra());

        List<String> opcionesIngles = new ArrayList<>();
        List<String> opcionesFrances = new ArrayList<>();

        String[] traducciones = palabraActual.getTraduccion().split(",");
        String correctaIngles = traducciones[0].trim();
        String correctaFrances = traducciones[1].trim();

        opcionesIngles.add(correctaIngles);
        opcionesFrances.add(correctaFrances);

        ArrayList<Palabra> copia = new ArrayList<>(listaPalabras);
        Collections.shuffle(copia);

        for (Palabra p : copia) {
            String[] t = p.getTraduccion().split(",");
            String en = t[0].trim();
            String fr = t[1].trim();

            if (!opcionesIngles.contains(en)) opcionesIngles.add(en);
            if (!opcionesFrances.contains(fr)) opcionesFrances.add(fr);

            if (opcionesIngles.size() == 3 && opcionesFrances.size() == 3) break;
        }

        Collections.shuffle(opcionesIngles);
        Collections.shuffle(opcionesFrances);

        comboBoxIngles.getItems().setAll(opcionesIngles);
        comboBoxFrances.getItems().setAll(opcionesFrances);

        comboBoxIngles.getSelectionModel().clearSelection();
        comboBoxFrances.getSelectionModel().clearSelection();
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
        if (palabraActual == null) return;

        String[] correctas = palabraActual.getTraduccion().split(",");
        String correctIngles = correctas[0].trim();
        String correctFrances = correctas[1].trim();

        String selecIngles = comboBoxIngles.getSelectionModel().getSelectedItem();
        String selecFrances = comboBoxFrances.getSelectionModel().getSelectedItem();

        if (selecIngles != null && selecIngles.equalsIgnoreCase(correctIngles))
            Resultados.setAciertosIngles(Resultados.getAciertosIngles() + 1);
        else
            Resultados.setFallosIngles(Resultados.getFallosIngles() + 1);

        if (selecFrances != null && selecFrances.equalsIgnoreCase(correctFrances))
            Resultados.setAciertosFrances(Resultados.getAciertosFrances() + 1);
        else
            Resultados.setFallosFrances(Resultados.getFallosFrances() + 1);

        nuevaPalabra();
    }
}
