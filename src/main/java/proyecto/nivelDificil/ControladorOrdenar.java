/**
 * Clase: ControladorOrdenar
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana ordenar.fxml,
 * encargada de mostrar frases desordenadas y que el usuario las
 * ordene correctamente
 * */

package proyecto.nivelDificil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import proyecto.DatabaseService;
import proyecto.Resultados;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ControladorOrdenar {

    @FXML private Label lblInstruccion;
    @FXML private FlowPane contenedorPalabras;
    @FXML private Button botonSiguiente;
    @FXML private Button botonMenu;
    @FXML private Label lblResultado;

    private final DatabaseService databaseService = new DatabaseService();
    private List<Frase> frases;
    private Frase fraseActual;
    private List<String> seleccionUsuario = new ArrayList<>();

    public static class Frase {
        String correcta;
        List<String> desordenadas;
        String idioma;

        public Frase(String correcta, String desordenadas, String idioma) {
            this.correcta = correcta;
            this.desordenadas = Arrays.asList(desordenadas.split(" "));
            this.idioma = idioma;
        }
    }

    /**Inicializa el controlador. Carga las frases desde la base de datos,
     * establece las acciones de los botones y muestra la primera frase*/
    @FXML
    public void initialize() {
        frases = databaseService.obtenerFrasesOrdenar();

        if (frases == null || frases.isEmpty()) {
            lblInstruccion.setText("No hay frases disponibles para ordenar.");
            botonSiguiente.setDisable(true);
            return;
        }
        conectarBD();
        cargarFrasesDesdeBD();

        mostrarFraseAleatoria();

        botonSiguiente.setOnAction(event -> mostrarFraseAleatoria());
        botonMenu.setOnAction(this::aceptarComprobar);
    }

    /**Establece la conexión con la base de datos para verificar la
     * conexión. Muestra un mensaje de error si no se puede conectar*/
    private void conectarBD() {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/idiomas", "root", "abc123.");
            System.out.println("Conectado a la base de datos.");
        } catch (SQLException e) {
            mostrarError("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**Carga todas las frases disponibles desde la base de datos y las
     * almacena en la lista de frases. Si hay errores muestra un mensaje*/
    private void cargarFrasesDesdeBD() {
        frases = new ArrayList<>();

        try (Connection conn = databaseService.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT correcta, desordenadas, idioma FROM ordenar")) {

            while (rs.next()) {
                String correcta = rs.getString("correcta");
                String desordenadas = rs.getString("desordenadas");
                String idioma = rs.getString("idioma");

                frases.add(new Frase(correcta, desordenadas, idioma));
            }

            Collections.shuffle(frases);

        } catch (SQLException e) {
            lblResultado.setText("Error al cargar frases: " + e.getMessage());
        }
    }

    /**Muestra una frase aleatoria desordenada en pantalla. Si no quedan
     * frases disponibles se desactiva el botón de 'siguiente'*/
    private void mostrarFraseAleatoria() {
        if (frases.isEmpty()) {
            lblInstruccion.setText("No hay más frases disponibles.");
            botonSiguiente.setDisable(true);
            return;
        }

        fraseActual = frases.remove(new Random().nextInt(frases.size()));

        contenedorPalabras.getChildren().clear();
        seleccionUsuario.clear();

        lblInstruccion.setText("Ordena las palabras para formar la frase correcta:");
        lblResultado.setText("");

        List<String> mezcladas = new ArrayList<>(fraseActual.desordenadas);
        Collections.shuffle(mezcladas);

        for (String palabra : mezcladas) {
            Button btnPalabra = new Button(palabra);
            btnPalabra.setStyle("-fx-font-size: 14px; -fx-padding: 5 10;");

            btnPalabra.setOnAction(event -> {
                seleccionUsuario.add(palabra);
                btnPalabra.setDisable(true);

                lblResultado.setText(String.join(" ", seleccionUsuario));
            });

            contenedorPalabras.getChildren().add(btnPalabra);
        }
    }

    /**Muestra una ventana de alerta con un mensaje de error*/
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**Combinación de los métodos aceptar y comprobar*/
    @FXML
    void aceptarComprobar(ActionEvent event) {
        comprobar();
        aceptar(event);
    }

    /**Guarda los resultados actuales en la base de datos y abre la
     * ventana del menú*/
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

    /**Comprueba si la frase ordenada es correcta y muestra una frase nueva*/
    @FXML
    void comprobar() {
        if (fraseActual == null) {
            lblResultado.setText("No hay frase cargada.");
            return;
        }

        String respuestaUsuario = String.join(" ", seleccionUsuario);
        boolean correcto = respuestaUsuario.equalsIgnoreCase(fraseActual.correcta);

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

        mostrarFraseAleatoria();
    }


}
