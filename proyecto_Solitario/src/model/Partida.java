package model;

public class Partida {

	//Atributos
	private Jugador jugador;
	private Tablero tablero;
	private boolean partidaTerminada;
	private int movimientos;
	private Dificultad dificultad;
	
	public Partida(Dificultad dificultad) {
		
		this.jugador = new Jugador();
		this.tablero = new Tablero();
		this.partidaTerminada = false;
		this.movimientos = 0;
		this.dificultad = dificultad;
		
	}
	
	public Partida(Jugador jugador){
		
		this.jugador = jugador;
		this.tablero = new Tablero();
		this.partidaTerminada = false;
		this.movimientos = 0;
		
	}

	
	//GETTERS Y SETTERS
	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public boolean isPartidaTerminada() {
		return partidaTerminada;
	}

	public void setPartidaTerminada(boolean partidaTerminada) {
		this.partidaTerminada = partidaTerminada;
	}

	public int getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(int movimientos) {
		this.movimientos = movimientos;
	}
	
	//METODOS VARIOS
	
	
	 // Inicia la partida repartiendo las cartas iniciales en el tablero
    public void iniciarPartida() {
        tablero.repartirInicial();
        System.out.println("Partida iniciada para el jugador: " + jugador.getNombreUsuario());
    }

    // Pide cartas del mazo y las manda al descarte
    public void pedirCartas() {
        tablero.pedirCartasDelMazo(dificultad.getCartasARobar());
        movimientos++;
    }

    // Muestra el estado actual de la partida
    public void mostrarPartida() {
        System.out.println("Jugador: " + jugador.getNombreUsuario());
        System.out.println("Movimientos: " + movimientos);
        tablero.mostrarTablero();
    }

    // Termina la partida
    public void terminarPartida() {
        partidaTerminada = true;
        System.out.println("La partida ha terminado.");
    }

    // Comprueba si la partida sigue activa
    public boolean partidaActiva() {
        return !partidaTerminada;
    }

    @Override
    public String toString() {
        return "Partida de " + jugador.getNombreUsuario() 
                + " | Movimientos: " + movimientos 
                + " | Terminada: " + partidaTerminada;
    }
	
	
	
	
	
	
}
