package controller;
import model.Carta;
import model.ColorCarta;
import model.Palo;

public class Main {

	public static void main(String[] args) {
		
		Carta carta1 = new Carta(13, Palo.PICAS, ColorCarta.NEGRO);
		
		try {
			
			carta1.insertarCarta();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
