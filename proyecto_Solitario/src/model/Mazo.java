package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.DaoMazo;

public class Mazo {
	
    // Lista de cartas disponibles para robar
	private List<Carta> cartas = new ArrayList<Carta>();
	
	public Mazo() {
	}
	
	public Mazo(List<Carta> cartas) {
		this.cartas = cartas;
	}

	//===================GETTER Y SETTER=================
	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}
	
	//=========METODOS MAZO=======================
	
	// Baraja el mazo aleatoriamente con Collections.shuffle()
    // Se llama en Tablero.repartirInicial() antes de repartir
	
	public void barajar() {
		Collections.shuffle(cartas); 
	}
	
	// Método para robar la carta superior
	// Roba la carta de la posición 0, la quita del mazo y la pone boca arriba
    // Devuelve null si el mazo está vacío 

	public Carta robarCarta() {
		if(cartas.isEmpty()) {
			return null;
		}
		
		Carta cartaRobada = cartas.remove(0);
		cartaRobada.setBocaArriba(true);
		
		return cartaRobada;
	}
	
	// Roba varias cartas según la dificultad (1, 2 o 3)
    // Usamos while con boolean, si el mazo se vacía antes sale del while

	
	public List<Carta> robarCartas(int cantidad) {
		List<Carta> cartasRobadas = new ArrayList<>();
		boolean seguirRobando = true;
		int i = 0;
		
		while(i < cantidad && seguirRobando) {
			Carta carta = robarCarta();
			
			if(carta != null) {
				cartasRobadas.add(carta);
				i++;
			} else {
			    // Si no quedan cartas, se pone en false para poder salir 
				seguirRobando = false; 
			}
		}
		
		return cartasRobadas;
	}
	
	//estos métodos sirven para agregar las cartas de la pila de descartes al mazo de nuevo
	public void agregarCarta(Carta cartaIn) {
		cartas.add(cartaIn);
	}
	
	public void agregarCartas(List<Carta> nuevasCartas) {
		//el método addAll() es un método que agrega todos los objetos que estén en el arraylist que le llega
		cartas.addAll(nuevasCartas);
	}
	
	//este método comprueba cuando el mazo se queda sin cartas
	public boolean estaVacia() {
		return cartas.isEmpty();
	}
	
	public int numeroCartas() {
		return cartas.size();
	}
	
	public void seleccionarTodasCartas () throws SQLException{
		
		cartas= DaoMazo.getInstance().selectAll().getCartas();
				
	}
	
	public void insertarCartas() throws SQLException {
		for(int i=0;i<cartas.size();i++) {
			cartas.get(i).insertarCarta();
			
		}
	}
	
	public void imprimirInfoCartas() {
		System.out.println(toString());
	}
	
	//====== MÉTODOS DE IMPRESIÓN ============
		
   
    public String toString() {
        String resCadena = "Mazo actual:\n";

        for (int i = 0; i < cartas.size(); i++) {
            resCadena += cartas.get(i).toString() + "\n";   
        }

        return resCadena;
    }
}