package model;

import java.sql.SQLException;
import dao.DaoCarta;

// La unidad básica del juego. De ella dependen Mazo, Descarte, Fundacion y Tablero
public class Carta {

	private int valor; // 1 (As) al 13 (Rey)
	private Palo palo; // CORAZONES, DIAMANTES, TREBOLES o PICAS
	private ColorCarta color; // ROJO o NEGRO
	private boolean bocaArriba; // false = oculta, true = visible

	public Carta() {
	}

	// bocaArriba empieza en false: las cartas al crearse están boca abajo
	
	public Carta(int valor, Palo palo, ColorCarta color) {
		this.valor = valor;
		this.palo = palo;
		this.color = color;
		this.bocaArriba = false;
	}

	// ======== GETTERS Y SETTERS ========
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}

	public ColorCarta getColor() {
		return color;
	}

	public void setColor(ColorCarta color) {
		this.color = color;
	}

	public boolean isBocaArriba() {
		return bocaArriba;
	}

	public void setBocaArriba(boolean b) {
		this.bocaArriba = b;
	}

	// ======== MÉTODOS ========

	// Voltea la carta oculta 
	public void girar() {
		this.bocaArriba = !this.bocaArriba;
	}

	public boolean esRoja() {
		return this.color == ColorCarta.ROJO;
	}

	public boolean esNegro() {
		return this.color == ColorCarta.NEGRO;
	}

	// Traduce el valor numérico a texto
	public String getNombreValor() {
		switch (valor) {
		case 1:
			return "A";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return String.valueOf(valor);
		}
	}

	// Construye la representación visual con colores ANSI
	// Estructura: [fondo blanco][color palo][valor][símbolo][reset]
	public String getNombreCompleto() {
		return "\u001B[47m\u001B[30m\u001B[1m" + "[" + palo.getCodigoColor() + getNombreValor() + palo.getSimbolo()
				+ "\u001B[47m\u001B[30m" + "]" + "\u001B[0m";
	}
	
	// Método para insertar carta de una en una en la BBBDD
	public void insertarCarta() throws SQLException {
		DaoCarta.getInstance().insertCarta(this);
	}

	// Carta oculta: fondo oscuro. Carta visible: colores ANSI
	
	@Override
	public String toString() {
		if (!bocaArriba) {
			return "\u001B[40m\u001B[37m[\uD83C\uDCA0]\u001B[0m";
		}
		return getNombreCompleto();
	}
}
