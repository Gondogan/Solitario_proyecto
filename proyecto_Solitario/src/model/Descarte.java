package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Descarte {

	//========= ATRIBUTOS =========
	
	private List<Carta> cartas;
	
	//========= CONSTRUCTOR =======
	
	public Descarte() {
		this.cartas = new ArrayList<>();
	}

	//======== GETTER Y SETTER =====================
	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}
	
	//============ METODOS DE DESCARTE =============
	
	//método para agregar cartas a la pila de descarte, agrega una carta
	public void agregarCarta(Carta carta) {
		
		if(carta != null) {
			carta.setBocaArriba(true);
			cartas.add(carta);
		}
	}
	
	// Añade varias cartas llamando a agregarCarta() por cada una
	//método complementario al de arriba donde agregamos las cartas a la pila descarte y se van almacenando
	public void agregarCartas(List<Carta> cartasNuevas) {
		
		if(cartasNuevas != null) {
			for(Carta carta : cartasNuevas) {
				agregarCarta(carta);
			}
		}
	}
	
	// Mira la última carta sin quitarla (size()-1 porque indexa desde 0)
    // Devuelve null si el descarte está vacío

	public Carta verUltimaCarta() {
		if(estaVacio()) {
			return null;
		}
		//devolvemos el tamaño menos 1 porque el tamaño real es 1, 2 o 3 y su posición real es uno menos, es decir, 0, 1 o 2
		return cartas.get(numeroCartas() - 1);
	}

    // Quita y devuelve la última carta cuando el jugador la mueve
	
	public Carta quitarUltimaCarta() {
		
		if(estaVacio()) {
			return null;
		}
		// lo mismo que arriba pero quitando la carta al moverla
		return cartas.remove(numeroCartas() - 1);
	}
	
	public boolean estaVacio() {
		
		return cartas.isEmpty();
	}
	
	public int numeroCartas() {
		
		return cartas.size();
	}
	
	// Devuelve todas las cartas al mazo en orden inverso boca abajo
    // Collections.reverse() da la vuelta: la última pasa a ser la primera

	public List<Carta> devolverCartasAlMazo(){
		
		List<Carta> cartasParaMazo = new ArrayList<>(cartas);
		
		cartas.clear();
		//Collections tiene el método reverse(List<>) que lo que hace es dar la vuelta la lista, es decir, la última pasa a ser la primera y viceversa
		Collections.reverse(cartasParaMazo);
		
		for(Carta carta : cartasParaMazo) {
			carta.setBocaArriba(false);
		}
		
		return cartasParaMazo;
	}
	
	@Override
	
	public String toString() {
		if(estaVacio()) {
			
			return "Descarte vacio";
		}
		
		return "Descartes: " + verUltimaCarta();
	}
	
}
