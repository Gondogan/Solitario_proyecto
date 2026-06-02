package model;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

	// 7 columnas del tableau, cada una es una lista de cartas
	private ArrayList<ArrayList<Carta>> columnas;

	// 4 fundaciones (una por palo): el objetivo del juego
	private ArrayList<Fundacion> fundaciones;

	// Mazo llega ya cargado de la BBDD desde Partida
	// No lo creamos aquí para no mezclar Model con DAO
	private Mazo mazo;

	private Descarte descarte;

	// Recibe el mazo con las 52 cartas, crea columnas vacías y 4 fundaciones
	public Tablero(Mazo mazo) {
		this.mazo = mazo;
		this.descarte = new Descarte();

		this.columnas = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			columnas.add(new ArrayList<Carta>());
		}

		this.fundaciones = new ArrayList<>();
		fundaciones.add(new Fundacion(Palo.CORAZONES));
		fundaciones.add(new Fundacion(Palo.DIAMANTES));
		fundaciones.add(new Fundacion(Palo.TREBOLES));
		fundaciones.add(new Fundacion(Palo.PICAS));
	}

	// ======== GETTERS Y SETTERS ========
	public ArrayList<ArrayList<Carta>> getColumnas() {
		return columnas;
	}

	public void setColumnas(ArrayList<ArrayList<Carta>> c) {
		this.columnas = c;
	}

	public ArrayList<Fundacion> getFundaciones() {
		return fundaciones;
	}

	public void setFundaciones(ArrayList<Fundacion> f) {
		this.fundaciones = f;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	public Descarte getDescarte() {
		return descarte;
	}

	public void setDescarte(Descarte descarte) {
		this.descarte = descarte;
	}

	// ======== MÉTODOS DE COLUMNAS ========

	// Valida que el índice esté en rango (0-6) antes de acceder
	public boolean existeColumna(int n) {
		return n >= 0 && n < columnas.size();
	}

	public void agregarCartaAColumna(int n, Carta carta) {
		if (existeColumna(n)) {
			columnas.get(n).add(carta);
		} else if (carta == null) {
	        System.out.println("No se puede añadir una carta nula."); 
		}else {
			System.out.println("La columna " + n + " no existe.");
		}
	}

	// Mira sin quitar. Devuelve null si la columna está vacía
	public Carta obtenerUltimaCarta(int n) {
		ArrayList<Carta> col = columnas.get(n);
		if (col.isEmpty()) {
			return null;
		}
		return col.get(col.size() - 1);
	}

	// Quita y devuelve. Devuelve null si la columna está vacía
	public Carta quitarUltimaCarta(int n) {
		ArrayList<Carta> col = columnas.get(n);
		if (col.isEmpty()) {
			return null;
		}
		return col.remove(col.size() - 1);
	}

	// ======== REPARTO INICIAL ========

	// Baraja y reparte: C1=1, C2=2... C7=7 cartas. Solo la última boca arriba
	// Total: 28 cartas al tableau, 24 quedan en el mazo
	public void repartirInicial() {
		mazo.barajar();
		for (int i = 0; i < columnas.size(); i++) {
			for (int j = 0; j <= i; j++) {
				Carta carta = mazo.robarCarta();
				if (carta != null) {
					// j == i → es la última de esa columna → boca arriba
					if (j == i) {
						carta.setBocaArriba(true);
					} else {
						carta.setBocaArriba(false);
					}
					columnas.get(i).add(carta);
				}
			}
		}
	}

	// ======== MAZO Y DESCARTE ========

	// Si el mazo está vacío recicla el descarte; si no, roba según la dificultad
	// Usamos if-else en lugar de return para controlar el flujo sin cortarlo
	public void pedirCartasDelMazo(int cartasARobar) {
	    if (mazo.estaVacia()) {
	        reciclarDescarteAMazo();
	    } else {
	        List<Carta> pedidas = mazo.robarCartas(cartasARobar);
	        descarte.agregarCartas(pedidas);
	    }
	}

	// Devuelve el descarte al mazo. Si ambos están vacíos avisa y no hace nada
	public void reciclarDescarteAMazo() {
		if (descarte.estaVacio()) {
			System.out.println("No hay cartas en el descarte para devolver al mazo.");
		} else {
			List<Carta> devueltas = descarte.devolverCartasAlMazo();
			for (Carta carta : devueltas) {
				carta.setBocaArriba(false);
			}
			mazo.agregarCartas(devueltas);
			System.out.println("Mazo reciclado con las cartas del descarte.");
		}
	}
}
