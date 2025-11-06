package proyecto;

import proyecto.nivelMedio.ControladorEspacio;
import proyecto.nivelDificil.ControladorOrdenar;

import java.sql.*;
import java.util.*;

public class DatabaseService {

    private static final String URL = "jdbc:mysql://localhost:3306/idiomas";
    private static final String USER = "root";
    private static final String PASSWORD = "abc123.";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void inicializarBaseDeDatos() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS idiomas");
            stmt.executeUpdate("USE idiomas");


            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS columnas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    palabra_es VARCHAR(100) NOT NULL,
                    palabra_en VARCHAR(100) NOT NULL,
                    palabra_fr VARCHAR(100) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS ordenar (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    correcta VARCHAR(300) NOT NULL,
                    desordenadas VARCHAR(300) NOT NULL,
                    idioma VARCHAR(20) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS espacio (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    texto VARCHAR(300) NOT NULL,
                    respuesta VARCHAR(100) NOT NULL,
                    idioma VARCHAR(20) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS seleccionar (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    pregunta VARCHAR(300) NOT NULL,
                    correcta VARCHAR(150) NOT NULL,
                    incorrecta1 VARCHAR(150) NOT NULL,
                    incorrecta2 VARCHAR(150) NOT NULL,
                    idioma VARCHAR(20) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS traducir (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    palabra VARCHAR(100) NOT NULL,
                    traduccion VARCHAR(100) NOT NULL,
                    idioma_origen VARCHAR(20) NOT NULL,
                    idioma_destino VARCHAR(20) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS adivinar (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    pista VARCHAR(300) NOT NULL,
                    respuesta VARCHAR(100) NOT NULL,
                    idioma VARCHAR(20) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS resultados (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    aciertos_es INT DEFAULT 0,
                    fallos_es INT DEFAULT 0,
                    aciertos_en INT DEFAULT 0,
                    fallos_en INT DEFAULT 0,
                    aciertos_fr INT DEFAULT 0,
                    fallos_fr INT DEFAULT 0
                )
            """);

            insertarDatosIniciales(stmt);

            System.out.println("✅ Base de datos inicializada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al inicializar base de datos: " + e.getMessage());
        }
    }

    private void insertarDatosIniciales(Statement stmt) throws SQLException {

        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM columnas");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
                INSERT INTO columnas (palabra_es, palabra_en, palabra_fr) VALUES
                ('casa','house','maison'),
                ('perro','dog','chien'),
                ('libro','book','livre')
            """);
        }


        rs = stmt.executeQuery("SELECT COUNT(*) FROM ordenar");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
                INSERT INTO ordenar (correcta, desordenadas, idioma) VALUES
                ('I am learning English', 'learning am I English', 'en'),
                ('Je suis très content', 'content suis Je très', 'fr'),
                ('Estoy estudiando ahora', 'estudiando Estoy ahora', 'es')
            """);
        }


        rs = stmt.executeQuery("SELECT COUNT(*) FROM espacio");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
                INSERT INTO espacio (texto, respuesta, idioma) VALUES
                ('I ___ a book', 'have', 'en'),
                ('Je ___ une pomme', 'mange', 'fr'),
                ('Yo ___ fútbol', 'juego', 'es')
            """);
        }


        rs = stmt.executeQuery("SELECT COUNT(*) FROM seleccionar");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
                INSERT INTO seleccionar (pregunta, correcta, incorrecta1, incorrecta2, idioma) VALUES
                ('¿Cómo se dice "gato" en inglés?', 'cat', 'dog', 'house', 'es'),
                ('Which is a fruit?', 'apple', 'chair', 'shirt', 'en'),
                ('Comment dit-on "table" en espagnol?', 'mesa', 'puerta', 'coche', 'fr')
            """);
        }


        rs = stmt.executeQuery("SELECT COUNT(*) FROM traducir");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
                INSERT INTO traducir (palabra, traduccion, idioma_origen, idioma_destino) VALUES
                ('water','agua','en','es'),
                ('luna','moon','es','en'),
                ('maison','casa','fr','es')
            """);
        }


        rs = stmt.executeQuery("SELECT COUNT(*) FROM adivinar");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
                INSERT INTO adivinar (pista, respuesta, idioma) VALUES
                ('Animal que ladra', 'perro', 'es'),
                ('You drink it', 'water', 'en'),
                ('On le lit', 'livre', 'fr')
            """);
        }
    }


}
