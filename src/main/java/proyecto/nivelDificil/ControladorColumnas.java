package proyecto.nivelDificil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import proyecto.DatabaseService;
import proyecto.Palabra;
import proyecto.Resultados;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ControladorColumnas {

    @FXML
    private Button botonMenu;

    @FXML
    private Button botonSiguiente;

    @FXML
    private ListView<String> columnaEspañol;

    @FXML
    private ListView<String> columnaFrances;

    @FXML
    private ListView<String> columnaIngles;

    private ArrayList<Palabra> listaPalabras = new ArrayList<>();
    private Palabra palabraActual;
    private Random aleatorio = new Random();

    @FXML
    public void initialize() {
        cargarPalabrasDesdeBD();
        nuevaPalabra();

        botonMenu.setOnAction(this::aceptarComprobar);
        botonSiguiente.setOnAction(event -> comprobar());
    }

    private void cargarPalabrasDesdeBD() {
        DatabaseService db = new DatabaseService();
        listaPalabras.clear();

        String sql = "SELECT palabra_es, traduccion_en, traduccion_fr FROM traducir";

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
            columnaEspañol.getItems().setAll("FIN");
            columnaIngles.setDisable(true);
            columnaFrances.setDisable(true);
            botonSiguiente.setDisable(true);
            return;
        }

        int index = aleatorio.nextInt(listaPalabras.size());
        palabraActual = listaPalabras.remove(index);

        columnaEspañol.getItems().setAll(palabraActual.getPalabra());

        ArrayList<String> ingles = new ArrayList<>();
        ArrayList<String> frances = new ArrayList<>();

        for (Palabra p : listaPalabras) {
            String[] t = p.getTraduccion().split(",");
            ingles.add(t[0].trim());
            frances.add(t[1].trim());
        }

        String[] correctas = palabraActual.getTraduccion().split(",");
        ingles.add(correctas[0].trim());
        frances.add(correctas[1].trim());

        Collections.shuffle(ingles);
        Collections.shuffle(frances);

        columnaIngles.getItems().setAll(ingles);
        columnaFrances.getItems().setAll(frances);

        columnaIngles.getSelectionModel().clearSelection();
        columnaFrances.getSelectionModel().clearSelection();
    }

    @FXML
    void aceptarComprobar(ActionEvent event) {
        comprobar();
        aceptar(event);
    }

    @FXML
    void aceptar(ActionEvent event) {
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
        String correctaIngles = correctas[0].trim();
        String correctaFrances = correctas[1].trim();

        String seleccionIngles = columnaIngles.getSelectionModel().getSelectedItem();
        String seleccionFrances = columnaFrances.getSelectionModel().getSelectedItem();

        if (seleccionIngles != null && seleccionIngles.equalsIgnoreCase(correctaIngles))
            Resultados.setAciertosIngles(Resultados.getAciertosIngles() + 1);
        else
            Resultados.setFallosIngles(Resultados.getFallosIngles() + 1);

        if (seleccionFrances != null && seleccionFrances.equalsIgnoreCase(correctaFrances))
            Resultados.setAciertosFrances(Resultados.getAciertosFrances() + 1);
        else
            Resultados.setFallosFrances(Resultados.getFallosFrances() + 1);

        nuevaPalabra();
    }
}
