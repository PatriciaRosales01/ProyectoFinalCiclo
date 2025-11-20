/**
 * Clase: Resultados
 * Autor: Patricia Rosales
 * Fecha: Octubre-Noviembre 2025
 * Descripción: Clase que mantiene los resultados acumulados de
 * los ejercicios por idioma. Almacena los aciertos y fallos de
 * cada idioma y proporciona métodos estáticos para acceder y
 * modificarlos
 * */

package proyecto;

public class Resultados {

    private static int aciertosEspañol = 0;
    private static int fallosEspañol = 0;
    private static int aciertosIngles = 0;
    private static int fallosIngles = 0;
    private static int aciertosFrances = 0;
    private static int fallosFrances = 0;

    public static int getAciertosEspañol() { return aciertosEspañol; }
    public static int getFallosEspañol() { return fallosEspañol; }
    public static int getAciertosIngles() { return aciertosIngles; }
    public static int getFallosIngles() { return fallosIngles; }
    public static int getAciertosFrances() { return aciertosFrances; }
    public static int getFallosFrances() { return fallosFrances; }

    public static void setAciertosEspañol(int aciertosEspañol) {
        Resultados.aciertosEspañol = aciertosEspañol;
    }

    public static void setFallosEspañol(int fallosEspañol) {
        Resultados.fallosEspañol = fallosEspañol;
    }

    public static void setAciertosIngles(int aciertosIngles) {
        Resultados.aciertosIngles = aciertosIngles;
    }

    public static void setFallosIngles(int fallosIngles) {
        Resultados.fallosIngles = fallosIngles;
    }

    public static void setAciertosFrances(int aciertosFrances) {
        Resultados.aciertosFrances = aciertosFrances;
    }

    public static void setFallosFrances(int fallosFrances) {
        Resultados.fallosFrances = fallosFrances;
    }


    /**Suma los resultados obtenidos en una actividad a los generales.*/
    public static void añadirResultados(int ae, int fe, int ai, int fi, int af, int ff) {
        aciertosEspañol += ae;
        fallosEspañol += fe;
        aciertosIngles += ai;
        fallosIngles += fi;
        aciertosFrances += af;
        fallosFrances += ff;
    }


    /**Reinicia los contadores de aciertos y falllos globales*/
    public static void resetActividad() {
        aciertosEspañol = 0;
        fallosEspañol = 0;
        aciertosIngles = 0;
        fallosIngles = 0;
        aciertosFrances = 0;
        fallosFrances = 0;
    }



}
