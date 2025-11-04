module palabra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens proyecto to javafx.fxml;
    exports proyecto;
    exports proyecto.nivelDificil;
    opens proyecto.nivelDificil to javafx.fxml;
    exports proyecto.nivelFacil;
    opens proyecto.nivelFacil to javafx.fxml;
    exports proyecto.nivelMedio;
    opens proyecto.nivelMedio to javafx.fxml;
}