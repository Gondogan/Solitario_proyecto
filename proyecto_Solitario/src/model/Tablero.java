package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tablero {

	//ATRIBUTOS
    private ArrayList<ArrayList<Carta>> columnas;
    private Mazo mazo;
    private Descarte descarte;

    public Tablero() {
        columnas = new ArrayList<>();
        mazo = new Mazo();
        descarte = new Descarte();

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

    public Mazo getMazo() {
        return mazo;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public Descarte getDescarte() {
        return descarte;
    }

    public void setDescarte(Descarte descarte) {
        this.descarte = descarte;
    }
    
    public boolean existeColumna(int numeroColumna) {
    	return numeroColumna >= 0 && numeroColumna <columnas.size();
    }

 // Añade una carta a una columna si la columna existe.
    public void agregarCartaAColumna(int numeroColumna, Carta carta) {
        if (existeColumna(numeroColumna)) {
            columnas.get(numeroColumna).add(carta);
        } else {
            System.out.println("La columna " + numeroColumna + " no existe.");
        }
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

    public void repartirInicial() {
        mazo.barajar();

        for (int i = 0; i < columnas.size(); i++) {
            for (int j = 0; j <= i; j++) {

                Carta carta = mazo.robarCarta();

                if (carta != null) {
                    if (j == i) {
                        carta.setBocaArriba(true);
                    } else {
                        carta.setBocaArriba(false);
                    }

                    columnas.get(i).add(carta);
                }
            }
        }
    }

    //METODOS VARIOS
    public void pedirCartasDelMazo(int cartasARobar) {
    	// Si el mazo está vacío, reciclamos las cartas del descarte y terminamos el método
    	if (mazo.estaVacia()) {
    	    reciclarDescarteAMazo();
    	    return;
    	}

    	// Robamos 3 cartas del mazo
    	List<Carta> cartasPedidas = mazo.robarCartas(cartasARobar);

    	// Invertimos el orden para que se coloquen correctamente en el descarte
    	Collections.reverse(cartasPedidas);

    	// Ponemos todas las cartas robadas boca arriba
    	for (Carta carta : cartasPedidas) {
    	    carta.setBocaArriba(true);
    	}

    	// Añadimos las cartas robadas al descarte
    	descarte.agregarCartas(cartasPedidas);
    }

    public void reciclarDescarteAMazo() {
        if (descarte.estaVacio()) {
            System.out.println("No hay cartas en el descarte para devolver al mazo.");
            return; 
        //con este return nos aseguramos de que no se devuelvan cartas que no hay
            }

        //pedimos que descarte nos devuelva todas sus cartas
        List<Carta> cartasDevueltas = descarte.devolverCartasAlMazo();

        for (Carta carta : cartasDevueltas) {
            carta.setBocaArriba(false);
            //setBocaArriba = false porque al volver al mazo dejan de estar visibles
        }


        mazo.agregarCartas(cartasDevueltas);
    }

  
    public void mostrarTablero() {
        System.out.println("MAZO: " + mazo.numeroCartas() + " cartas");

        System.out.println("DESCARTE: ");
        if (descarte.estaVacio()) {
            System.out.println("Vacío");
        } else {
            System.out.println(descarte.verUltimaCarta());
        }

        System.out.println("\nCOLUMNAS:");

        for (int i = 0; i < columnas.size(); i++) {
            System.out.println("Columna " + (i + 1) + ":");

            for (Carta carta : columnas.get(i)) {
                System.out.println(carta);
            }

            System.out.println("--------------------");
        }
    }

}