package dao;

import model.Partida;
import java.sql.*;

public class DaoPartida {

	// Conexión a la base de datos, la obtenemos de DbConnection
	private Connection conn = null;

	// Instancia única del DAO, siguiendo el patrón Singleton
	// Solo existirá un objeto DaoPartida durante toda la ejecución
	public static DaoPartida instance = null;

	// Método para obtener la instancia única
	// Si no existe la creamos, si ya existe la devolvemos
	public static DaoPartida getInstance() throws SQLException {

		if (instance == null) {
			instance = new DaoPartida();
		}

		return instance;
	}

	// Constructor: obtiene la conexión compartida de DbConnection
	public DaoPartida() throws SQLException {
		conn = DbConnection.getConnection();
	}

	// ======== MÉTODOS ========

	// Guarda el resultado de una partida al terminar
	// Insertamos: id del jugador, movimientos, si ganó y tiempo en segundos
	// El tiempo lo guardamos en segundos porque es un entero fácil de ordenar en el
	// ranking
	public boolean guardarPartida(Partida partida) throws SQLException {

		String insertPartida = "INSERT INTO partidas (id_jugador, movimientos, ganada, tiempo_segundos) "
				+ "VALUES (?, ?, ?, ?)";

		PreparedStatement nuevaPartida = conn.prepareStatement(insertPartida);

		// Necesitamos el id del jugador para la clave foránea de la tabla partidas
		nuevaPartida.setInt(1, partida.getJugador().getId());
		nuevaPartida.setInt(2, partida.getMovimientos());
		nuevaPartida.setBoolean(3, partida.isGanada());

		// Guardamos los segundos en bruto, no el formato "2 min 5 seg"
		// porque en la BBDD queremos números para poder ordenar por tiempo
		nuevaPartida.setInt(4, partida.getTiempoSegundos());

		int partidaInsertada = nuevaPartida.executeUpdate();
		nuevaPartida.close();

		if (partidaInsertada > 0) {
			System.out.println("Partida guardada correctamente.");
			return true;
		} else {
			System.out.println("No se ha podido guardar la partida.");
			return false;
		}
	}

	// Muestra el ranking de jugadores ordenado por número de victorias
	// Si dos jugadores tienen las mismas victorias, aparece primero el que tardó
	// menos
	// JOIN une partidas con jugadores para obtener el nombre
	// GROUP BY agrupa todas las partidas de cada jugador
	// WHERE ganada = true cuenta solo las victorias
	public void mostrarRanking() throws SQLException {

		String selectRanking = "SELECT j.nombre_usuario, " + "COUNT(*) AS partidas_ganadas, "
				+ "MIN(p.movimientos) AS mejor_movimientos, " + "MIN(p.tiempo_segundos) AS mejor_tiempo "
				+ "FROM partidas p " + "JOIN jugadores j ON p.id_jugador = j.id " + "WHERE p.ganada = true "
				+ "GROUP BY j.nombre_usuario " + "ORDER BY partidas_ganadas DESC, mejor_tiempo ASC";

		Statement consultaRanking = conn.createStatement();
		ResultSet resultadoRanking = consultaRanking.executeQuery(selectRanking);

		System.out.println("\n===== RANKING =====");

		int posicion = 1;
		boolean hayResultados = resultadoRanking.next();

		while (hayResultados) {

			String nombre = resultadoRanking.getString("nombre_usuario");
			int ganadas = resultadoRanking.getInt("partidas_ganadas");
			int movimientos = resultadoRanking.getInt("mejor_movimientos");

			// Los segundos los convertimos al formato legible solo para mostrar por
			// pantalla
			String tiempo = formatearTiempo(resultadoRanking.getInt("mejor_tiempo"));

			System.out.println(posicion + ". " + nombre + " | Victorias: " + ganadas + " | Mejor marca: " + movimientos
					+ " mov en " + tiempo);

			posicion++;
			hayResultados = resultadoRanking.next();
		}

		if (posicion == 1) {
			System.out.println("Todavía no hay partidas ganadas.");
		}

		System.out.println("===================\n");

		resultadoRanking.close();
		consultaRanking.close();
	}

	// Muestra el historial completo de partidas de un jugador concreto
	// Incluye todas las partidas, ganadas y perdidas, de más reciente a más antigua
	// ORDER BY id DESC → las más recientes primero porque tienen id más alto
	public void mostrarHistorial(int idJugador) throws SQLException {

		String selectHistorial = "SELECT ganada, movimientos, tiempo_segundos " + "FROM partidas "
				+ "WHERE id_jugador = ? " + "ORDER BY id DESC";

		PreparedStatement consultaHistorial = conn.prepareStatement(selectHistorial);
		consultaHistorial.setInt(1, idJugador);

		ResultSet resultadoHistorial = consultaHistorial.executeQuery();

		System.out.println("\n===== TU HISTORIAL =====");

		int num = 1;
		boolean hayPartidas = resultadoHistorial.next();

		while (hayPartidas) {

			// Convertimos el boolean de la BBDD a un texto claro para el jugador
			String resultado = resultadoHistorial.getBoolean("ganada") ? "GANADA ✓" : "PERDIDA ✗";
			int movimientos = resultadoHistorial.getInt("movimientos");

			// Los segundos los convertimos al formato legible solo para mostrar por
			// pantalla
			String tiempo = formatearTiempo(resultadoHistorial.getInt("tiempo_segundos"));

			System.out.println(num + ". " + resultado + " | " + movimientos + " movimientos" + " | Tiempo: " + tiempo);

			num++;
			hayPartidas = resultadoHistorial.next();
		}

		if (num == 1) {
			System.out.println("Todavía no tienes partidas.");
		}

		System.out.println("========================\n");

		resultadoHistorial.close();
		consultaHistorial.close();
	}

	// Convierte segundos a un formato legible para mostrar por pantalla
	// En la BBDD guardamos segundos en bruto para poder ordenar
	// pero al jugador le mostramos minutos y segundos porque es más intuitivo
	// Ejemplo: 125 → "2 min 5 seg"
	// Ejemplo: 45 → "45 seg"
	private String formatearTiempo(int segundos) {

		int minutos = segundos / 60;
		int segsRestantes = segundos % 60;

		if (minutos == 0) {
			return segsRestantes + " seg";
		}

		return minutos + " min " + segsRestantes + " seg";
	}
}