/**
 * Clase: ResultadoControlador
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Controlador encargado de la gestión de la base de
 * datos. Se encarga de inicializar la base de datos y sus tablas,
 * insertar los distintos datos dependiendo de cada actividad
 * */

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
                CREATE TABLE IF NOT EXISTS escoger (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    palabra_es VARCHAR(100) NOT NULL,
                    traduccion_en VARCHAR(100) NOT NULL,
                    traduccion_fr VARCHAR(100) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS traducir (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    palabraEspañol VARCHAR(100) NOT NULL,
                    traduccionIngles VARCHAR(100) NOT NULL,
                    traduccionFrances VARCHAR(100) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS adivinar (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    pista TEXT NOT NULL,
                    respuesta_es TEXT NOT NULL,
                    respuesta_en TEXT NOT NULL,
                    respuesta_fr TEXT NOT NULL
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

            System.out.println("Base de datos inicializada correctamente.");

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
            ('cama','bed','lit'),
            ('manzana','apple','pomme'),
            ('coche','car','voiture'),
            ('ciudad','city','ville'),
            ('mar','sea','mer'),
            ('fuego','fire','feu'),
            ('hielo','ice','glace'),
            ('vino','wine','vin'),
            ('carne','meat','viande'),
            ('pescado','fish','poisson'),
            ('bosque','forest','forêt'),
            ('canción','song','chanson'),
            ('dinero','money','argent'),
            ('escuela','school','école'),
            ('profesor','teacher','professeur'),
            ('hermano','brother','frère'),
            ('hermana','sister','soeur'),
            ('reloj','clock','horloge'),
            ('jardín','garden','jardin'),
            ('montaña','mountain','montagne')
        """);
        }

        rs = stmt.executeQuery("SELECT COUNT(*) FROM escoger");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
            INSERT INTO escoger (palabra_es, traduccion_en, traduccion_fr) VALUES
            ('tienda','store','magasin'),
            ('plato','plate','assiette'),
            ('cuchara','spoon','cuillère'),
            ('cielo','sky','ciel'),
            ('estrella','star','étoile'),
            ('río','river','rivière'),
            ('llave','key','clé'),
            ('diente','tooth','dent'),
            ('mano','hand','main'),
            ('cabeza','head','tête'),
            ('pelo','hair','cheveux'),
            ('ojo','eye','œil'),
            ('camisa','shirt','chemise'),
            ('ropa','clothes','vêtements'),
            ('playa','beach','plage'),
            ('isla','island','île'),
            ('puente','bridge','pont'),
            ('tren','train','train'),
            ('taza','cup','tasse'),
            ('pantera','panther','panthère')
        """);
        }

        rs = stmt.executeQuery("SELECT COUNT(*) FROM ordenar");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
            INSERT INTO ordenar (correcta, desordenadas, idioma) VALUES
            ('I am reading a book', 'reading am I a book', 'en'),
            ('You are my friend', 'are friend You my', 'en'),
            ('She has a beautiful house', 'house She a has beautiful', 'en'),
            ('Je mange une pomme', 'pomme Je une mange', 'fr'),
            ('Je vais à l´école', 'école vais Je à l´', 'fr'),
            ('Il aime le chocolat', 'chocolat Il le aime', 'fr'),
            ('Estoy limpiando mi habitación', 'mi Estoy habitación limpiando', 'es'),
            ('Ella está comiendo ahora', 'está ahora Ella comiendo', 'es'),
            ('Nosotros vamos al parque', 'vamos parque Nosotros al', 'es'),
            ('They play football every day', 'every play They day football', 'en'),
            ('Nous buvons du lait', 'du Nous lait buvons', 'fr'),
            ('Yo leo cada noche', 'leo Yo noche cada', 'es'),
            ('He likes music', 'music likes He', 'en'),
            ('Je regarde la télé', 'télé Je regarde la', 'fr'),
            ('Tú estudias inglés', 'inglés Tú estudias', 'es'),
            ('I want some water', 'water some want I', 'en'),
            ('Je prends le bus', 'bus Je le prends', 'fr'),
            ('Ella tiene un perro', 'perro tiene Ella un', 'es'),
            ('We visit the museum', 'visit museum We the', 'en'),
            ('Ils regardent un film', 'film Ils un regardent', 'fr')
        """);
        }

        rs = stmt.executeQuery("SELECT COUNT(*) FROM espacio");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
            INSERT INTO espacio (texto, respuesta, idioma) VALUES
            ('I ___ coffee every morning', 'drink', 'en'),
            ('She ___ to school by bus', 'goes', 'en'),
            ('They ___ playing soccer', 'are', 'en'),
            ('Je ___ du pain', 'mange', 'fr'),
            ('Tu ___ une voiture', 'as', 'fr'),
            ('Nous ___ heureux', 'sommes', 'fr'),
            ('Yo ___ una casa', 'tengo', 'es'),
            ('Ella ___ muy alta', 'es', 'es'),
            ('Nosotros ___ en clase', 'estamos', 'es'),
            ('I ___ tired today', 'am', 'en'),
            ('Je ___ un livre', 'lis', 'fr'),
            ('Yo ___ música', 'escucho', 'es'),
            ('They ___ at home', 'are', 'en'),
            ('Tu ___ mon ami', 'es', 'fr'),
            ('Nosotros ___ al cine', 'vamos', 'es'),
            ('I ___ a question', 'have', 'en'),
            ('Elle ___ une robe', 'porte', 'fr'),
            ('Yo ___ feliz', 'estoy', 'es'),
            ('We ___ dinner now', 'are', 'en'),
            ('Vous ___ français', 'parlez', 'fr')
        """);
        }

        rs = stmt.executeQuery("SELECT COUNT(*) FROM traducir");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
        INSERT INTO traducir (palabraEspañol, traduccionIngles, traduccionFrances) VALUES
        ('fuego','fire','feu'),
        ('pan','bread','pain'),
        ('árbol','tree','arbre'),
        ('vino','wine','vin'),
        ('carne','meat','viande'),
        ('escuela','school','école'),
        ('manzana','apple','pomme'),
        ('nieve','snow','neige'),
        ('sol','sun','soleil'),
        ('zapato','shoe','chaussure'),
        ('libro','book','livre'),
        ('amor','love','amour'),
        ('botella','bottle','bouteille'),
        ('mesa','table','table'),
        ('playa','beach','plage'),
        ('tos','cough','toux'),
        ('bosque','forest','forêt'),
        ('leche','milk','lait'),
        ('cuchillo','knife','couteau'),
        ('ojo','eye','œil')
    """);
        }

        rs = stmt.executeQuery("SELECT COUNT(*) FROM adivinar");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.executeUpdate("""
            INSERT INTO adivinar (pista, respuesta_es, respuesta_en, respuesta_fr) VALUES
            ('Animal que dice miau', 'gato', 'cat', 'chat'),
            ('Brilla en el cielo', 'sol', 'sun', 'soleil'),
            ('Se usa para cortar comida', 'cuchillo', 'knife', 'couteau'),
            ('Sirve para beber agua', 'vaso', 'glass', 'verre'),
            ('Está en tu mano', 'dedo', 'finger', 'doigt'),
            ('Lo lees', 'libro', 'book', 'livre'),
            ('Se usa para escribir', 'lápiz', 'pencil', 'crayon'),
            ('Se bebe por la mañana', 'café', 'coffee', 'café'),
            ('Animal que da leche', 'vaca', 'cow', 'vache'),
            ('Fruta roja pequeña', 'fresa', 'strawberry', 'fraise'),
            ('Se conduce', 'coche', 'car', 'voiture'),
            ('Lugar donde aprendes', 'escuela', 'school', 'école'),
            ('Cae del cielo', 'lluvia', 'rain', 'pluie'),
            ('Se abre y se cierra', 'puerta', 'door', 'porte'),
            ('Sirve para ver', 'ojo', 'eye', 'œil'),
            ('Lugar con árboles', 'bosque', 'forest', 'forêt'),
            ('Cubre la cabeza', 'sombrero', 'hat', 'chapeau'),
            ('Vuela en el cielo', 'pájaro', 'bird', 'oiseau'),
            ('Se usa para dormir', 'cama', 'bed', 'lit'),
            ('Es blanco y frío', 'hielo', 'ice', 'glace')
        """);
        }
    }



    public List<ControladorOrdenar.Frase> obtenerFrasesOrdenar() {
        List<ControladorOrdenar.Frase> frases = new ArrayList<>();

        String sql = "SELECT correcta, desordenadas, idioma FROM ordenar";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                frases.add(new ControladorOrdenar.Frase(
                        rs.getString("correcta"),
                        rs.getString("desordenadas"),
                        rs.getString("idioma")));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener frases para ordenar: " + e.getMessage());
        }

        return frases;
    }

    public List<ControladorEspacio.Frase> obtenerFrasesCompletar() {
        List<ControladorEspacio.Frase> lista = new ArrayList<>();

        String sql = "SELECT texto, respuesta, idioma FROM espacio";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new ControladorEspacio.Frase(
                        rs.getString("texto"),
                        rs.getString("respuesta"),
                        rs.getString("idioma")));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener frases: " + e.getMessage());
        }

        return lista;
    }





    public void guardarResultados() {
        String sqlSelect = "SELECT COUNT(*) FROM resultados";
        String sqlInsert = """
        INSERT INTO resultados (aciertos_es, fallos_es, aciertos_en, fallos_en, aciertos_fr, fallos_fr)
        VALUES (?, ?, ?, ?, ?, ?)
        """;
        String sqlUpdate = """
        UPDATE resultados SET
        aciertos_es = ?, fallos_es = ?, aciertos_en = ?, fallos_en = ?, aciertos_fr = ?, fallos_fr = ?
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {

            rs.next();
            boolean hayRegistro = rs.getInt(1) > 0;

            PreparedStatement ps;
            if (hayRegistro) {
                ps = conn.prepareStatement(sqlUpdate);
            } else {
                ps = conn.prepareStatement(sqlInsert);
            }

            ps.setInt(1, Resultados.getAciertosEspañol());
            ps.setInt(2, Resultados.getFallosEspañol());
            ps.setInt(3, Resultados.getAciertosIngles());
            ps.setInt(4, Resultados.getFallosIngles());
            ps.setInt(5, Resultados.getAciertosFrances());
            ps.setInt(6, Resultados.getFallosFrances());

            ps.executeUpdate();
            ps.close();

            System.out.println("Resultados guardados en la base de datos.");

        } catch (SQLException e) {
            System.err.println("Error al guardar resultados: " + e.getMessage());
        }
    }


    public void cargarResultados() {
        String sql = "SELECT aciertos_es, fallos_es, aciertos_en, fallos_en, aciertos_fr, fallos_fr FROM resultados LIMIT 1";

        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Resultados.setAciertosEspañol(rs.getInt("aciertos_es"));
                Resultados.setFallosEspañol(rs.getInt("fallos_es"));
                Resultados.setAciertosIngles(rs.getInt("aciertos_en"));
                Resultados.setFallosIngles(rs.getInt("fallos_en"));
                Resultados.setAciertosFrances(rs.getInt("aciertos_fr"));
                Resultados.setFallosFrances(rs.getInt("fallos_fr"));
            }

        } catch (Exception e) {
            System.out.println("Error cargando resultados: " + e.getMessage());
        }
    }



}
