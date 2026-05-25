package model;

import java.util.ArrayList;

public class Tablero {

    private ArrayList<ArrayList<Carta>> columnas;

    public Tablero() {
        columnas = new ArrayList<>();

        // Creamos las 7 columnas del solitario
        for (int i = 0; i < 7; i++) {
        	
            columnas.add(new ArrayList<Carta>());
        }
    }

    public ArrayList<ArrayList<Carta>> getColumnas() {
        return columnas;
    }

    public void setColumnas(ArrayList<ArrayList<Carta>> columnas) {
        this.columnas = columnas;
    }
    
    
    public void agregarCartaAColumna(int numeroColumna, Carta carta) {
        columnas.get(numeroColumna).add(carta);
    }
    
    
    public Carta obtenerUltimaCarta(int numeroColumna) {
        ArrayList<Carta> columna = columnas.get(numeroColumna);

        if (columna.isEmpty()) {
            return null;
        }

        return columna.get(columna.size() - 1);
    }
    
    public Carta quitarUltimaCarta(int numeroColumna) {
        ArrayList<Carta> columna = columnas.get(numeroColumna);

        if (columna.isEmpty()) {
            return null;
        }

        return columna.remove(columna.size() - 1);
    }
    
    public void mostrarTablero() {
        for (int i = 0; i < columnas.size(); i++) {
            System.out.println("Columna " + (i + 1) + ":");

            for (Carta carta : columnas.get(i)) {
                System.out.println(carta);
            }

            System.out.println("--------------------");
        }
    }
}
