package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Descarte {

	private List<Carta> cartas;
	
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
	
	//método complementario al de arriba donde agregamos las cartas a la pila descarte y se van almacenando
	public void agregarCartas(List<Carta> cartasNuevas) {
		
		if(cartasNuevas != null) {
			for(Carta carta : cartasNuevas) {
				agregarCarta(carta);
			}
		}
	}
	
	//con este método veremos la última carta agregada a la pila descarte sin quitarla
	public Carta verUltimaCarta() {
		if(estaVacio()) {
			return null;
		}
		//devolvemos el tamaño menos 1 porque el tamaño real es 1, 2 o 3 y su posición real es uno menos, es decir, 0, 1 o 2
		return cartas.get(numeroCartas() - 1);
	}

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
	
	//método para devolver las cartas de la pila de descarte al mazo en el orden correcto, es decir, de forma inversa a la que se han ido apilando
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
