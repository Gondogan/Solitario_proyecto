package model;

import java.util.ArrayList;

import model.Carta;
import model.Tablero;

public class VistaTablero {

    /*
     * Este método es el principal de la vista.
     * Recibe un objeto Tablero y muestra por consola:
     * - el mazo
     * - el descarte
     * - las 7 columnas del solitario
     */
    public void mostrar(Tablero tablero) {

        System.out.println("\n==================== SOLITARIO ====================");

        // Mostramos la parte superior del tablero: mazo y descarte
        mostrarMazoYDescarte(tablero);

        System.out.println("\n---------------------------------------------------");

        // Mostramos las columnas principales del solitario
        mostrarColumnas(tablero);

        System.out.println("===================================================\n");
    }

    /*
     * Este método muestra el mazo y el descarte.
     * El mazo se muestra como [###] porque las cartas están boca abajo.
     * El descarte muestra la última carta visible.
     */
    private void mostrarMazoYDescarte(Tablero tablero) {

        System.out.print("Mazo: ");

        // Si el mazo está vacío, mostramos [vacío]
        if (tablero.getMazo().estaVacia()) {
            System.out.print("[vacío]");
        } else {
            // Si tiene cartas, mostramos una carta boca abajo y el número de cartas restantes
            System.out.print("[###] (" + tablero.getMazo().numeroCartas() + ")");
        }

        System.out.print("     Descarte: ");

        // Si el descarte está vacío, mostramos [vacío]
        if (tablero.getDescarte().estaVacio()) {
            System.out.println("[vacío]");
        } else {
            // Si hay cartas, mostramos la última carta del descarte
            System.out.println(formatoCarta(tablero.getDescarte().verUltimaCarta()));
        }
    }

    /*
     * Este método muestra las 7 columnas del tablero.
     * Las imprime de forma gráfica, alineadas una al lado de otra.
     */
    private void mostrarColumnas(Tablero tablero) {

        // Guardamos las columnas del tablero en una variable para trabajar más cómodo
        ArrayList<ArrayList<Carta>> columnas = tablero.getColumnas();

        // Mostramos los nombres de las columnas: C1, C2, C3...
        for (int i = 0; i < columnas.size(); i++) {
            System.out.print("C" + (i + 1) + "\t");
        }

        System.out.println();

        // Calculamos cuál es la columna que tiene más cartas
        int alturaMaxima = calcularAlturaMaxima(columnas);

        /*
         * Recorremos el tablero por filas.
         * Esto permite imprimir las columnas alineadas horizontalmente.
         */
        for (int fila = 0; fila < alturaMaxima; fila++) {

            // Recorremos cada columna en esa fila concreta
            for (int columna = 0; columna < columnas.size(); columna++) {

                /*
                 * Si la columna tiene carta en esa fila, la mostramos.
                 * Si no tiene carta, dejamos un espacio vacío.
                 */
                if (fila < columnas.get(columna).size()) {
                    Carta carta = columnas.get(columna).get(fila);
                    System.out.print(formatoCarta(carta) + "\t");
                } else {
                    System.out.print("     \t");
                }
            }

            System.out.println();
        }
    }

    /*
     * Este método calcula cuántas filas necesitamos imprimir.
     * Para eso busca la columna con más cartas.
     */
    private int calcularAlturaMaxima(ArrayList<ArrayList<Carta>> columnas) {

        int alturaMaxima = 0;

        // Recorremos todas las columnas
        for (ArrayList<Carta> columna : columnas) {

            // Si esta columna tiene más cartas que la altura actual, actualizamos el valor
            if (columna.size() > alturaMaxima) {
                alturaMaxima = columna.size();
            }
        }

        return alturaMaxima;
    }

    /*
     * Este método convierte una carta en un texto corto para mostrarla en consola.
     * Si la carta está boca abajo, se muestra como [###].
     * Si está boca arriba, se muestra con su valor y palo.
     */
 // Código para resetear el color después de imprimir una carta
    private static final String RESET = "\u001B[0m";

    // Convierte una carta en texto para mostrarla en consola
    private String formatoCarta(Carta carta) {

        // Si no hay carta, dejamos un espacio vacío
        if (carta == null) {
            return "     ";
        }

        // Si la carta está boca abajo, no mostramos su valor
        if (!carta.isBocaArriba()) {
            return "[###]";
        }

        // Guardamos el valor de la carta: A, 2, 3, J, Q, K...
        String valor = carta.getNombreValor();

        // Guardamos el símbolo del palo: ♥, ♦, ♣, ♠
        String simbolo = carta.getPalo().getSimbolo();

        // Guardamos el color que tiene ese palo
        String color = carta.getPalo().getCodigoColor();

        // Devolvemos la carta con color, valor y símbolo
        return color + "[" + valor + simbolo + "]" + RESET;
    }
}