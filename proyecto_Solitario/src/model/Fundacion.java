package model;

import java.util.ArrayList;

public class Fundacion {

	// ATRIBUTOS
	// Cada fundacion tiene su propio palo (corazones, picas, etc)
	// y un ArrayList donde se van apilando las cartas en orden
	// Sería un array pero no hay tantos mñetodos propios de array y tendríamos que llevar un contador
	// y este no nos diría cual es el valor de la última carta sino cuantas están dentro del array
	
	private Palo palo;
	private ArrayList<Carta> cartas;

	// Códigos ANSI para mostrar el hueco vacío de la fundacion
	// Fondo negro con el color del símbolo del palo
	private static final String FONDO_VACIO  = "\u001B[40m"; // fondo negro
	private static final String COLOR_ROJO   = "\u001B[31m"; // texto rojo (corazones y diamantes vacíos)
	private static final String COLOR_BLANCO = "\u001B[37m"; // texto blanco (treboles y picas vacíos)
	private static final String NEGRITA      = "\u001B[1m";
	private static final String RESET        = "\u001B[0m";

	// CONSTRUCTOR
	// Al crear una fundacion le decimos a qué palo pertenece
	// y el ArrayList de cartas empieza vacío
	
	public Fundacion(Palo palo) {
		this.palo   = palo;
		this.cartas = new ArrayList<>();
	}

	// ======== GETTERS Y SETTERS ========

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public ArrayList<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(ArrayList<Carta> cartas) {
		this.cartas = cartas;
	}

	// ======== MÉTODOS ========

	// Comprueba si la fundacion está vacía
	
	public boolean estaVacia() {
		return cartas.isEmpty();
	}

	// Comprueba si la fundacion está completa, es decir, tiene las 13 cartas
	// As(1) → 2 → 3 → ... → Rey(13)
	
	public boolean estaCompleta() {
		return cartas.size() == 13;
	}

	// Devuelve cuántas cartas hay en la fundacion en ese momento
	
	public int numeroCartas() {
		return cartas.size();
	}

	// Miramos la última carta de la fundacion sin quitarla
	// Igual que en Descarte, usamos size()-1 para obtener la posición real
	
	public Carta verUltimaCarta() {
		if (estaVacia()) {
			return null;
		}
		return cartas.get(numeroCartas() - 1);
	}

	// Sacamos la última carta de la fundacion para devolverla al tablero
	// Solo se puede sacar la última, igual que en el Descarte
	
	public Carta quitarUltimaCarta() {
		if (estaVacia()) {
			return null;
		}
		return cartas.remove(numeroCartas() - 1);
	}

	// Este método comprueba si la carta que queremos meter puede entrar en esta fundacion
	// sin moverla todavía, solo miramos si cumple las reglas
	// DAR UNA VUELTA NO ME GUSTA CON UN RETURN AL FINAL
	public boolean aceptaCarta(Carta carta) {

		// La carta debe ser siempre del mismo palo que la fundacion
		if (carta.getPalo() != this.palo) {
			return false;
		}

		// Si la fundacion está vacía, solo puede entrar el As (valor 1)
		if (estaVacia()) {
			return carta.getValor() == 1;
		}

		// Si ya tiene cartas, la nueva debe valer exactamente 1 más que la última
		// Ejemplo: si la última es un 5, solo puede entrar un 6
		
		return carta.getValor() == verUltimaCarta().getValor() + 1;
	}

	// Este método intenta meter la carta en la fundacion
	// Primero comprueba si puede entrar con aceptaCarta()
	// Si puede, la añade y devuelve true. Si no puede, devuelve false
	public boolean agregarCarta(Carta carta) {

		if (!aceptaCarta(carta)) {
			return false;
		}

		// La carta siempre va boca arriba en la fundacion
		carta.setBocaArriba(true);
		cartas.add(carta);
		return true;
	}

	// Devuelve el color del símbolo cuando la fundacion está vacía
	// Corazones y Diamantes → rojo | Treboles y Picas → blanco
	
	private String getColorVacio() {
		if (palo == Palo.CORAZONES || palo == Palo.DIAMANTES) {
			return COLOR_ROJO;
		} else {
			return COLOR_BLANCO;
		}
	}

	// ======== toString ========

	@Override
	public String toString() {

		// Si está vacía: fondo negro + símbolo del palo en su color
		if (estaVacia()) {
			return FONDO_VACIO + NEGRITA + getColorVacio()
				 + "[ " + palo.getSimbolo() + " ]"
				 + RESET;
		}

		// Si tiene cartas: mostramos la última con sus colores normales
		// El toString() de Carta ya lleva fondo blanco + negrita + color del palo
		return "[F" + palo.getSimbolo() + ": " + verUltimaCarta().toString() + "]";
	}
}