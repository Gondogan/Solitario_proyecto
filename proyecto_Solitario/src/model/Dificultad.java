package model;

//Enum que representa los 3 niveles de dificultad del juego
//Guardamos la cantidad dentro del enum para no necesitar
//condicionales por todo el código

public enum Dificultad {

	FACIL(1), // roba 1 carta del mazo
	MEDIA(2), // roba 2 cartas del mazo
	DIFICIL(3); // roba 3 cartas del mazo

	private int cartasARobar;

	Dificultad(int cantidad) {
		this.cartasARobar = cantidad;
	}

	// Devuelve cuántas cartas hay que robar según la dificultad
	// Lo usan Tablero.pedirCartasDelMazo() y MenuPartida.pedirCartasDelMazo()

	public int getCartasARobar() {
		return cartasARobar;
	}
}
