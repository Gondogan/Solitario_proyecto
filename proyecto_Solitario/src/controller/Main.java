package controller;
import java.util.List;

import model.Carta;
import model.ColorCarta;
import model.Descarte;
import model.Palo;

public class Main {

	public static void main(String[] args) {
		
		/*Carta carta1 = new Carta(13, Palo.TREBOLES, ColorCarta.NEGRO);
		System.out.println("Funcionaa");
		
		try {
			
			carta1.insertarCarta();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		Descarte descarte = new Descarte();

        Carta carta1 = new Carta( 1, Palo.CORAZONES, ColorCarta.ROJO);
        Carta carta2 = new Carta( 13, Palo.PICAS, ColorCarta.NEGRO);

        descarte.agregarCarta(carta1);
        descarte.agregarCarta(carta2);

        System.out.println(descarte);
        System.out.println("Última carta: " + descarte.verUltimaCarta());

        Carta cartaMovida = descarte.quitarUltimaCarta();

        System.out.println("Carta movida: " + cartaMovida);
        System.out.println(descarte);

        List<Carta> cartasParaMazo = descarte.devolverCartasAlMazo();

        System.out.println("Cartas devueltas al mazo: " + cartasParaMazo.size());
        System.out.println("Descarte después: " + descarte);

	}

}
