package model;

// Enum que representa los 4 palos de la baraja de poker
// Usamos enum en lugar de String para que sea imposible escribir un palo incorrecto
// El compilador solo acepta CORAZONES, DIAMANTES, TREBOLES o PICAS
// Cada palo lleva su símbolo y su código de color ANSI para mostrarse en consola

public enum Palo {

	// Palos rojos: fondo blanco + texto rojo + negrita
	CORAZONES("\u2665", "\u001B[47m\u001B[31m\u001B[1m"), 
	DIAMANTES("\u2666", "\u001B[47m\u001B[31m\u001B[1m"),

	// Palos negros: fondo blanco + texto negro + negrita
	TREBOLES("\u2663", "\u001B[47m\u001B[30m\u001B[1m"), 
	PICAS("\u2660", "\u001B[47m\u001B[30m\u001B[1m");

	private final String simbolo;
	private final String codigoColor;

	// RESET cancela todos los efectos ANSI al final de cada carta impresa
	// Sin él el color se "contagiaría" al texto que viene después
	
	private static final String RESET = "\u001B[0m";

	// CONSTRUCTOR DEL ENUM: cada valor recibe su símbolo y código de color
	
	Palo(String simbolo, String codigoColor) {
		this.simbolo = simbolo;
		this.codigoColor = codigoColor;
	}

	// Devuelve el símbolo visual del palo: ♥ ♦ ♣ ♠
	// Lo usa Carta en getNombreCompleto() para construir la representación visual
	
	public String getSimbolo() {
		return simbolo;
	}

	// Devuelve el código ANSI de color del palo
	// Lo usa Carta para aplicar el color correcto antes de imprimir
	
	public String getCodigoColor() {
		return codigoColor;
	}

	// Devuelve el nombre del palo con color y símbolo aplicados
	
	String toStringColor() {
		return codigoColor + this.name() + simbolo + RESET;
	}
}
