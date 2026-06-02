package model;

import java.sql.SQLException;

import dao.DaoCarta;

public class Carta {

	

	private int valor;
	private Palo palo;
	private ColorCarta color;
	private boolean bocaArriba;
	
	public Carta() {
	}
	
	public Carta(int valor, Palo palo, ColorCarta color) {
		
		this.valor = valor;
		this.palo = palo;
		this.color = color;
		this.bocaArriba = false;
	}

	
	//================GETTERS Y SETTERS===================
	

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

	public void setBocaArriba(boolean bocaArriba) {
		this.bocaArriba = bocaArriba;
	}
	
	//==========METODOS CARTAS=========================
	
	public void girar() {
		this.bocaArriba = !this.bocaArriba;
	}
	
	public boolean esRoja() {
		return this.color == ColorCarta.ROJO;
	}
	
	public boolean esNegro() {
		return this.color == ColorCarta.NEGRO;
	}
	
	//algunas cartas de poker no tienen valor númerico sino que son letras, es decir, son String.

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
	//para hacerlo homogéneo convertimos el resto de valores de las cartas, que si son numéricos, a tipo String
		default:
			return String.valueOf(valor);
		}
	}
	
	public String getNombreCompleto() {
		return  "\u001B[47m\u001B[30m\u001B[1m" + "[" + palo.getCodigoColor() + getNombreValor() + palo.getSimbolo() + "\u001B[47m\u001B[30m" + "]" + "\u001B[0m" ;
	}
	
	public void insertarCarta() throws SQLException{
		
		DaoCarta.getInstance().insertCarta(this);
	}
	
	
	
	@Override
	
	public String toString() {
		
		if(!bocaArriba) {
			return "[carta oculta]";
			
		}
		
		return getNombreCompleto();
	}
	
}
