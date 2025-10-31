module palabra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens proyecto to javafx.fxml;
    exports proyecto;
}