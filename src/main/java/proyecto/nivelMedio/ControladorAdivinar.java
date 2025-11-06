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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT pista, respuesta, idioma FROM adivinar");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Palabra p = new Palabra();
                p.setPista(rs.getString("pista"));
                p.setPalabra(rs.getString("respuesta"));
                p.setTraduccion("");
                listaPalabras.add(p);
            }

            System.out.println("Se cargaron " + listaPalabras.size() + " elementos desde la tabla 'adivinar'.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al cargar datos desde la tabla adivinar.");
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
            frase.setText("Has terminado todas las palabras. Pulsa aceptar para volver al menú.");
            español.setDisable(true);
            ingles.setDisable(true);
            frances.setDisable(true);
        }
    }


    @FXML
    void aceptarComprobar(ActionEvent event) {
        comprobar(event);
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
    void comprobar(ActionEvent event) {
        String respuestaUsuario = español.getText().trim();

        if (respuestaUsuario.equalsIgnoreCase(palabraActual.getPalabra())) {
            Resultados.setAciertosEspañol(Resultados.getAciertosEspañol() + 1);
        } else {
            Resultados.setFallosEspañol(Resultados.getFallosEspañol() + 1);
        }

        nuevaPalabra();
    }

}
