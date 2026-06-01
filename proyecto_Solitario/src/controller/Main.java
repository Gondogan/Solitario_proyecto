package controller;
import java.sql.SQLException;
import java.util.List;

import dao.DaoCarta;
import model.Carta;
import model.ColorCarta;
import model.Descarte;
import model.Fundacion;
import model.Mazo;
import model.Palo;

public class Main {

	public static void main(String[] args) {
    // ===== TEST FUNDACION =====

    // 1. Creamos las 4 fundaciones vacías, una por palo
    Fundacion fCorazones = new Fundacion(Palo.CORAZONES);
    Fundacion fDiamantes = new Fundacion(Palo.DIAMANTES);
    Fundacion fTreboles  = new Fundacion(Palo.TREBOLES);
    Fundacion fPicas     = new Fundacion(Palo.PICAS);
    
    // 2. Mostramos cómo se ven vacías (fondo negro + símbolo en color)
    System.out.println("--- Fundaciones VACÍAS ---");
    System.out.println(fCorazones);
    System.out.println(fDiamantes);
    System.out.println(fTreboles);
    System.out.println(fPicas);

    // 3. Intentamos meter una carta que NO es un As (debe fallar)
    System.out.println("\n--- Intentamos meter un 5 de Corazones (debe fallar) ---");
    Carta cartaMala = new Carta(5, Palo.CORAZONES, ColorCarta.ROJO);
    boolean resultado = fCorazones.agregarCarta(cartaMala);
    System.out.println("¿Se ha podido meter? " + resultado);
    System.out.println(fCorazones);

    // 4. Metemos el As de Corazones (debe funcionar)
    System.out.println("\n--- Metemos el As de Corazones (debe funcionar) ---");
    Carta as = new Carta(1, Palo.CORAZONES, ColorCarta.ROJO);
    resultado = fCorazones.agregarCarta(as);
    System.out.println("¿Se ha podido meter? " + resultado);
    System.out.println(fCorazones);

    // 5. Metemos el 2 de Corazones encima del As
    System.out.println("\n--- Metemos el 2 de Corazones ---");
    Carta dos = new Carta(2, Palo.CORAZONES, ColorCarta.ROJO);
    resultado = fCorazones.agregarCarta(dos);
    System.out.println("¿Se ha podido meter? " + resultado);
    System.out.println(fCorazones);

    // 6. Intentamos meter un 2 de PICAS en la fundacion de Corazones (palo incorrecto)
    System.out.println("\n--- Intentamos meter un 2 de Picas en Corazones (palo incorrecto) ---");
    Carta dosPicas = new Carta(2, Palo.PICAS, ColorCarta.NEGRO);
    resultado = fCorazones.agregarCarta(dosPicas);
    System.out.println("¿Se ha podido meter? " + resultado);
    System.out.println(fCorazones);

    // 7. Mostramos cuántas cartas tiene cada fundacion
    System.out.println("\n--- Número de cartas en cada fundacion ---");
    System.out.println("Corazones: " + fCorazones.numeroCartas());
    System.out.println("Diamantes: " + fDiamantes.numeroCartas());
    System.out.println("Treboles:  " + fTreboles.numeroCartas());
    System.out.println("Picas:     " + fPicas.numeroCartas());
	

	
	
	
	/*
	
	 public static void main(String[] args) {
		
		Mazo miMazo = new Mazo();
		
		try {
			
			miMazo.insertarCartas();
			miMazo.seleccionarTodasCartas();
			miMazo.imprimirInfoCartas();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//METODO PARA INSERTAR TODAS LAS CARTAS

		try {
            // Conectamos con la base de datos a través de tu DAO
            DaoCarta dao = DaoCarta.getInstance();
            int cartasInsertadas = 0;

            // Bucle externo: Recorre los 4 palos definidos en el Enum
            for (Palo palo : Palo.values()) {
                
                // Asignamos el color automáticamente
                ColorCarta color = ColorCarta.NEGRO;
                if (palo == Palo.CORAZONES || palo == Palo.DIAMANTES) {
                    color = ColorCarta.ROJO;
                }
                
                // Bucle interno: Crea los valores del 1 al 13 para el palo actual
                for (int valor = 1; valor <= 13; valor++) {
                    
                    // Creamos la carta usando el constructor de 3 parámetros
                    Carta nuevaCarta = new Carta(valor, palo, color);
                    
                    // La insertamos en la tabla usando el método que ya tenías
                    dao.insertCarta(nuevaCarta);
                    cartasInsertadas++;
                }
            }
            
            System.out.println("Se han insertado " + cartasInsertadas + " cartas en la base de datos.");
            
            
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
		
		*/
		
		
		
		
		/*Carta carta1 = new Carta(13, Palo.TREBOLES, ColorCarta.NEGRO);
		System.out.println("Funcionaa");
		
		try {
			
			carta1.insertarCarta();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	/*
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
   */
	}
}

