package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.DaoMazo;

public class Mazo {

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
	
	public void barajar() {
		Collections.shuffle(cartas); 
	}
	
	// Método para robar la carta superior
	public Carta robarCarta() {
		if(cartas.isEmpty()) {
			return null;
		}
		
		Carta cartaRobada = cartas.remove(0);
		cartaRobada.setBocaArriba(true);
		
		return cartaRobada;
	}
	
	// Método adaptado con WHILE para evitar el uso del 'break'
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
			    // Si no quedan cartas, apagamos la bandera para salir del bucle limpiamente
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