/**
 * Clase: Palabra
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Representa una palabra en un idioma determinado,
 * incluyendo una traducción y una pista. Proporciona métodos
 * para acceder y modificar sus atributos
 * */

package proyecto;

public class Palabra {
    private int id;
    private String palabra;
    private String traduccion;
    private String pista;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }
}
