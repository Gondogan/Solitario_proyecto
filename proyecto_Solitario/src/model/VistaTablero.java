package model;
 
import java.util.ArrayList;
 
public class VistaTablero {
 
    // Solo leemos el tablero, nunca lo modificamos
    private Tablero tablero;
 
    public VistaTablero(Tablero tablero) { this.tablero = tablero; }
 
    // Muestra: mazo + descarte | fundaciones | 7 columnas en horizontal
    public void mostrar() {
 
    	System.out.println("\n============================================================");

        System.out.print("MAZO: [" + tablero.getMazo().numeroCartas() + " cartas]   ");
        System.out.print("DESCARTE: ");
        if (tablero.getDescarte().estaVacio()) {
            System.out.print("[vacio]");
        } else {
            System.out.print(tablero.getDescarte().verUltimaCarta());
        }
        System.out.println();

        System.out.print("\nFUNDACIONES:  ");
        for (int i = 0; i < tablero.getFundaciones().size(); i++) {
            System.out.print(tablero.getFundaciones().get(i) + "  ");
        }
        System.out.println("\n");

        // Cabecera de columnas — cada cabecera ocupa exactamente 10 caracteres
        System.out.println("C1        C2        C3        C4        C5        C6        C7");
        System.out.println("------------------------------------------------------------------");

        ArrayList<ArrayList<Carta>> columnas = tablero.getColumnas();
 
        // Calculamos la altura máxima para saber cuántas filas imprimir
        int maxFilas = 0;
        for (int i = 0; i < columnas.size(); i++) {
            if (columnas.get(i).size() > maxFilas) {
                maxFilas = columnas.get(i).size();
            }
        }

        // Imprimimos fila a fila en horizontal
        // Cada celda ocupa exactamente 10 columnas visuales
        for (int fila = 0; fila < maxFilas; fila++) {
            for (int col = 0; col < columnas.size(); col++) {
                ArrayList<Carta> columna = columnas.get(col);
                if (fila < columna.size()) {
                    // Carta visible o boca abajo: imprimimos con relleno calculado
                    System.out.print(celdaCarta(columna.get(fila)));
                } else {
                    // Celda vacia: 10 espacios para mantener la alineacion
                    System.out.print("          ");
                }
            }
            System.out.println();
        }
 
        System.out.println("============================================================\n");
    }
    
    // Devuelve la carta como String con los espacios de relleno necesarios
    // para que cada celda ocupe exactamente 10 columnas visuales en la terminal.
    //
    // El problema: String.format NO sirve con codigos ANSI porque Java cuenta
    // los caracteres invisibles como parte del ancho del String.
    // La solucion: calculamos los espacios manualmente segun la longitud
    // conocida y fija de cada tipo de carta:
    //   - Carta OCULTA:         toString() devuelve 17 chars → ancho visual 3
    //   - Carta VISIBLE 1 dig:  toString() devuelve 46 chars → ancho visual 4
    //   - Carta VISIBLE 2 dig:  toString() devuelve 47 chars → ancho visual 5
    // Queremos que todas las celdas midan 10 columnas visuales.
    
    private String celdaCarta(Carta carta) {

        String s = carta.toString();
        int espacios;

        if (s.length() == 17) {
            // Carta oculta [🂠]: ancho visual 3 → necesitamos 7 espacios
            espacios = 7;
        } else if (s.length() == 47) {
            // Carta con valor 10: ancho visual 5 → necesitamos 5 espacios
            espacios = 5;
        } else {
            // Carta visible 1 digito (A,2..9,J,Q,K): ancho visual 4 → 6 espacios
            espacios = 6;
        }

        String relleno = "";
        int i = 0;
        while (i < espacios) {
            relleno += " ";
            i++;
        }

        return s + relleno;
    }
}
