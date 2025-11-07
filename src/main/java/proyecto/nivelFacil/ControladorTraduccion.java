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

    @FXML
    public void initialize() {
        cargarPalabrasDesdeBD();
        nuevaPalabra();
    }

    private void cargarPalabrasDesdeBD() {
        DatabaseService db = new DatabaseService();
        listaPalabras = new ArrayList<>();

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

            System.out.println("Se cargaron " + listaPalabras.size() + " palabras desde traducir.");

        } catch (SQLException e) {
            System.err.println("Error al cargar palabras: " + e.getMessage());
        }
    }


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
        String[] traducciones = palabraActual.getTraduccion().split(",");
        String palabraIngles = traducciones[0].trim();
        String palabraFrances = traducciones[1].trim();

        String ingles = traduccionIngles.getText().trim();
        String frances = traduccionFrances.getText().trim();

        if (ingles.equalsIgnoreCase(palabraIngles)) {
            Resultados.setAciertosIngles(Resultados.getAciertosIngles() + 1);
        } else {
            Resultados.setFallosIngles(Resultados.getFallosIngles() + 1);
        }

        if (frances.equalsIgnoreCase(palabraFrances)) {
            Resultados.setAciertosFrances(Resultados.getAciertosFrances() + 1);
        } else {
            Resultados.setFallosFrances(Resultados.getFallosFrances() + 1);
        }

        nuevaPalabra();
    }

}
