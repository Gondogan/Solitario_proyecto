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
        if (tablero.getDescarte().estaVacio()) { System.out.print("[vacío]"); }
        else { System.out.print(tablero.getDescarte().verUltimaCarta()); }
        System.out.println();
 
        System.out.print("FUNDACIONES:  ");
        for (int i = 0; i < tablero.getFundaciones().size(); i++) {
            System.out.print(tablero.getFundaciones().get(i) + "  ");
        }
        System.out.println("\n");
 
        System.out.println("   C1        C2        C3        C4        C5        C6        C7");
        System.out.println("-------------------------------------------------------------------");
 
        ArrayList<ArrayList<Carta>> columnas = tablero.getColumnas();
 
        // Calculamos la altura máxima para saber cuántas filas imprimir
        int maxFilas = 0;
        for (int i = 0; i < columnas.size(); i++) {
            if (columnas.get(i).size() > maxFilas) { maxFilas = columnas.get(i).size(); }
        }
 
        // Imprimimos fila a fila: columnas en horizontal para comparar de un vistazo
        for (int fila = 0; fila < maxFilas; fila++) {
            for (int col = 0; col < columnas.size(); col++) {
                ArrayList<Carta> columna = columnas.get(col);
                if (fila < columna.size()) { System.out.print(columna.get(fila) + "  "); }
                else                       { System.out.print("          "); }
            }
            System.out.println();
        }
 
        System.out.println("============================================================\n");
    }
}
