package model;

import java.sql.SQLException;

import dao.DaoMazo;

public class Partida {

	// ========= ATRIBUTOS
	private Jugador jugador;
	private Tablero tablero;
	private boolean partidaTerminada;
	private int movimientos;
	private Dificultad dificultad;
	

	// Si la partida ha terminado en victoria o en abandono
	// Lo necesita DaoPartida para guardarlo en la BBDD
	private boolean ganada;

	// El momento exacto en milisegundos en que empieza la partida
	// Lo usamos internamente para calcular el tiempo al terminar
	private long tiempoInicio;

	// El resultado final en segundos, que es lo que guardamos en la BBDD
	// Guardamos segundos y no minutos porque es un número entero más fácil de
	// ordenar en el ranking
	private int tiempoSegundos;

	// ==========CONSTRUCTORES
	
    // Carga las 52 cartas con DaoMazo y crea el Tablero con ellas
    // Lanza SQLException porque accede a la BBDD
    public Partida(Jugador jugador, Dificultad dificultad) throws SQLException {
        this.jugador          = jugador;
        this.dificultad       = dificultad;
        this.partidaTerminada = false;
        this.movimientos      = 0;
        this.ganada           = false;
        this.tiempoSegundos   = 0;
 
        // Cargamos el mazo de la BBDD aquí y no en Tablero
        // para no mezclar la capa Model con la capa DAO
        Mazo mazo    = DaoMazo.getInstance().selectAll();
        this.tablero = new Tablero(mazo);
    }
 

	// GETTERS Y SETTERS

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

	public boolean isGanada() {
		return ganada;
	}

	public void setGanada(boolean ganada) {
		this.ganada = ganada;
	}

	// getTiempoSegundos solo tiene getter porque el valor
	// lo calcula detenerTiempo(), no se setea desde fuera
	public int getTiempoSegundos() {
		return tiempoSegundos;
	}

	// MÉTODOS DE TIEMPO

	// Se llama justo cuando el jugador empieza a jugar
	// System.currentTimeMillis() nos da el tiempo actual en milisegundos
	// desde el 1 de enero de 1970, que es como Java mide el tiempo internamente
	public void iniciarTiempo() {
		tiempoInicio = System.currentTimeMillis();
	}

	// Se llama cuando la partida termina, ya sea por victoria o por abandono
	// Calculamos la diferencia entre ahora y cuando empezó
	// Dividimos entre 1000 para convertir milisegundos a segundos
	public void detenerTiempo() {
		long tiempoFin = System.currentTimeMillis();
		tiempoSegundos = (int) ((tiempoFin - tiempoInicio) / 1000);
	}

	// Convierte los segundos a un formato legible para mostrar por pantalla
	// En la BBDD guardamos segundos en bruto para poder ordenar en el ranking
	// pero al jugador le mostramos este formato más claro
	// Ejemplo: 125 segundos → "2 min 5 seg"
	// Ejemplo: 45 segundos → "45 seg"
	public String getTiempoFormateado() {

	    // Calculamos los segundos transcurridos AHORA mismo
	    // Si la partida ya terminó usamos tiempoSegundos guardado
	    // Si todavía está en curso calculamos la diferencia con el momento actual
	    int segundos;

	    if (partidaTerminada) {
	        segundos = tiempoSegundos;
	    } else {
	        segundos = (int) ((System.currentTimeMillis() - tiempoInicio) / 1000);
	    }

	    int minutos       = segundos / 60;
	    int segsRestantes = segundos % 60;

	    if (minutos == 0) {
	        return segsRestantes + " seg";
	    }

	    return minutos + " min " + segsRestantes + " seg";
	}


	// MÉTODOS VARIOS

	// Inicia la partida repartiendo las cartas iniciales en el tablero
	// y arranca el contador de tiempo
	public void iniciarPartida() {
		tablero.repartirInicial();
		iniciarTiempo();
		System.out.println("Partida iniciada para el jugador: " + jugador.getNombreUsuario());
	}

	// Pide cartas del mazo y las manda al descarte
	public void pedirCartas() {
		tablero.pedirCartasDelMazo(dificultad.getCartasARobar());
		movimientos++;
	}

	/*// Muestra el estado actual de la partida
	public void mostrarPartida() {
		System.out.println("Jugador: " + jugador.getNombreUsuario());
		System.out.println("Movimientos: " + movimientos);
		System.out.println("Tiempo: " + getTiempoFormateado());
		tablero.mostrarTablero();
	}
*/
	// Termina la partida, para el tiempo y marca como terminada
	public void terminarPartida() {
		detenerTiempo();
		partidaTerminada = true;
		System.out.println("La partida ha terminado.");
		System.out.println("Tiempo total: " + getTiempoFormateado());
	}

	// Comprueba si la partida sigue activa
	public boolean partidaActiva() {
		return !partidaTerminada;
	}

	@Override
	public String toString() {
		return "Partida de " + jugador.getNombreUsuario() + " | Movimientos: " + movimientos + " | Tiempo: "
				+ getTiempoFormateado() + " | Ganada: " + ganada + " | Terminada: " + partidaTerminada;
	}


	public Dificultad getDificultad() {
		
		return dificultad;
	}
}