package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {

	private List<Carta> cartas;
	
	public Mazo() {
		this.cartas = new ArrayList<>(cartas);
	}
	
	public Mazo(List<Carta> cartas) {
		this.cartas = cartas;
	}

	
	//===================GETTER Y SETTER=================
	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}
	
	//=========METODOS MAZO=======================
	
	public void barajar() {
		//Collections es una clase de java que contiene el método shuffle(List<>) que nos facilita el barajar el mazo de cartas
		Collections.shuffle(cartas); 
	}
	
	
	// con este método simularemos cuando se roba una carta del mazo
	
	public Carta robarCarta() {
		
		//si está vacia no se roba, devolvemos null
		if(cartas.isEmpty()) {
			return null;
		}
		
		//en caso de que si haya, la carta que robemos la quitamos del mazo y la seteamos boca arriba para que sea visible y retornamos la carta
		Carta cartaRobada = cartas.remove(0);
		cartaRobada.setBocaArriba(true);
		
		return cartaRobada;
	}
	
	//¡Importante! según la dificultad, porque si robas por ejemplo 2 cartas y solo queda una en el mazo solo se roba 1 carta,
	///no se devuelven todas las cartas de descartes al mazo
	
	public List<Carta> robarCartas(int cantidad){
		//creamos el arraylist donde se añadirán las cartas, que se lo pasaremos a la clase descartes
		List<Carta> cartasRobadas = new ArrayList<>();
		
		//nos recorremos el array de cartas robando tantas cartas como se indique según la dificultad seleccionada
		for(int i = 0; i < cantidad; i++) {
			Carta carta = robarCarta();
			
			if(carta != null) {
				cartasRobadas.add(carta);
			}
		}
		
		
		return cartasRobadas;
	}
	
	//estos métodos sirven para agregar las cartas de la pila de descartes al mazo de nuevo
	public void agregarCarta(Carta carta) {
		cartas.add(carta);
	}
	
	public void agregarCartas(List<Carta> nuevasCartas) {
		//el método addAll() es un método que agrega todos los objetos que estén en el arraylist que le llega
		cartas.addAll(nuevasCartas);
	}
	
	//este método comprueba cuando el mazo se queda sin cartas
	public boolean estaVacia() {
		return cartas.isEmpty();
	}
	
	//con este método comprobamos cuantas cartas quedan aún en el mazo por jugar
	public int numeroCartas() {
		return cartas.size();
	}
	
	
	@Override
	
	public String toString() {
		return "Mazo con " + numeroCartas() + " cartas";
	}
}
