package model;

public enum Dificultad {
	
	FACIL(1),
	MEDIA(2), 
	DIFICIL(3);
	
	private int cartasARobar;
	
	Dificultad(int cantidad){
		this.cartasARobar = cantidad;
	}
	
	public int getCartasARobar() {
		return cartasARobar;
	}
}
