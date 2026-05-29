package model;

public enum Palo {

	CORAZONES("♥", "\u001B[47m\u001B[31m\u001B[1m"), //fondo blanco + letras rojo + negrita
	DIAMANTES("♦", "\u001B[47m\u001B[31m\u001B[1m"), //fondo blanco + letras rojo + negrita 
	TREBOLES("♣", "\u001B[47m\u001B[30m\u001B[1m"), //fondo blanco + letras negro + negrita 
	PICAS("♠", "\u001B[47m\u001B[30m\u001B[1m"); //fondo blanco + letras negro + negrita
	
	private final String simbolo;
	private final String codigoColor;
	
	private static final String RESET = "\u001B[0m";
	
	Palo(String simbolo, String codigoColor){
		this.simbolo = simbolo;
		this.codigoColor = codigoColor;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public String getCodigoColor() {
		return codigoColor;
	}
	
	String toStringColor() {
		return codigoColor +this.name() + simbolo + RESET;
	}
	
}
