/**
 * Clase: ResultadoControlador
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador que gestiona la ventana resultado.fxml,
 * encargada de mostrar los resultados de los ejercicios en un
 * gráfico de barras para español, inglés y francés
 * */

package proyecto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class ResultadoControlador {

    @FXML
    private BarChart<String, Number> grafico;

    @FXML
    private Label nombre;

    /**Inicializa el controlador. Carga los resultados desde la base de datos
     * y los muestra en un gráfico de barras para cada idioma*/
    @FXML
    public void initialize() {
        new DatabaseService().cargarResultados();

        grafico.getData().clear();

        int ae = Resultados.getAciertosEspañol();
        int fe = Resultados.getFallosEspañol();
        int ai = Resultados.getAciertosIngles();
        int fi = Resultados.getFallosIngles();
        int af = Resultados.getAciertosFrances();
        int ff = Resultados.getFallosFrances();

        XYChart.Series<String, Number> serieEspañol = new XYChart.Series<>();
        serieEspañol.setName("Español");
        serieEspañol.getData().addAll(
                new XYChart.Data<>("Aciertos", ae),
                new XYChart.Data<>("Fallos", fe)
        );

        XYChart.Series<String, Number> serieIngles = new XYChart.Series<>();
        serieIngles.setName("Inglés");
        serieIngles.getData().addAll(
                new XYChart.Data<>("Aciertos", ai),
                new XYChart.Data<>("Fallos", fi)
        );

        XYChart.Series<String, Number> serieFrances = new XYChart.Series<>();
        serieFrances.setName("Francés");
        serieFrances.getData().addAll(
                new XYChart.Data<>("Aciertos", af),
                new XYChart.Data<>("Fallos", ff)
        );

        grafico.getData().addAll(serieEspañol, serieIngles, serieFrances);

        Platform.runLater(() -> {
            serieEspañol.getData().forEach(d ->
                    d.getNode().setStyle(d.getXValue().equals("Aciertos") ? "-fx-bar-fill: #FF0000;" : "-fx-bar-fill: #ec5353;")
            );
            serieIngles.getData().forEach(d ->
                    d.getNode().setStyle(d.getXValue().equals("Aciertos") ? "-fx-bar-fill: #FFA500;" : "-fx-bar-fill: #eca053;")
            );
            serieFrances.getData().forEach(d ->
                    d.getNode().setStyle(d.getXValue().equals("Aciertos") ? "-fx-bar-fill: #008000;" : "-fx-bar-fill: #12a14b;")
            );
        });
    }

    /**Cierra la aplicación*/
    @FXML
    void salir() {
        Platform.exit();
    }
}
