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
		nuevaPartida.setInt(1, partida.getJugador().getId());
		nuevaPartida.setInt(2, partida.getMovimientos());
		nuevaPartida.setBoolean(3, partida.isGanada());
		nuevaPartida.setInt(4, partida.getTiempoSegundos());

		int insertada = nuevaPartida.executeUpdate();
		nuevaPartida.close();

		if (insertada > 0) {
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

		String selectRanking = "SELECT j.nombre_usuario, " + "COUNT(*) AS ganadas, "
				+ "MIN(p.movimientos) AS mejor_movs, " + "MIN(p.tiempo_segundos) AS mejor_tiempo " + "FROM partidas p "
				+ "JOIN jugadores j ON p.id_jugador = j.id " + "WHERE p.ganada = true " + "GROUP BY j.nombre_usuario "
				+ "ORDER BY ganadas DESC, mejor_tiempo ASC";

		Statement consultaRanking = conn.createStatement();
		ResultSet resultadoRanking = consultaRanking.executeQuery(selectRanking);

		System.out.println("\n===== RANKING =====");
		// Anchos de cada columna (incluyendo espacios de margen)
		// Calculados para que quepan todos los valores posibles
		int aPos = 4; // "# " → máx 2 dígitos
		int aNombre = 18; // "Nombre" → máx 16 caracteres
		int aVict = 11; // "Victorias" → máx 3 dígitos
		int aMovs = 12; // "Mejor movs" → máx 3 dígitos
		int aTiempo = 15; // "Mejor tiempo" → máx "10 min 59 seg"

		String sep = separador(aPos, aNombre, aVict, aMovs, aTiempo);

		System.out.println();
		System.out.println(sep);
		// Cabecera de la tabla
		System.out.println(
				fila(aPos, aNombre, aVict, aMovs, aTiempo, "#", "Nombre", "Victorias", "Mejor movs", "Mejor tiempo"));
		System.out.println(sep);

		int pos = 1;
		boolean hayFilas = resultadoRanking.next();

		while (hayFilas) {
			String nombre = resultadoRanking.getString("nombre_usuario");
			String vict = String.valueOf(resultadoRanking.getInt("ganadas"));
			String movs = String.valueOf(resultadoRanking.getInt("mejor_movs"));
			String tiempo = formatearTiempo(resultadoRanking.getInt("mejor_tiempo"));

			System.out.println(
					fila(aPos, aNombre, aVict, aMovs, aTiempo, String.valueOf(pos), nombre, vict, movs, tiempo));

			pos++;
			hayFilas = resultadoRanking.next();
		}

		System.out.println(sep);

		// Si no había ninguna fila, avisamos dentro de la tabla
		if (pos == 1) {
			System.out.println("| Todavia no hay partidas ganadas.                               |");
			System.out.println(sep);
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

		// Anchos de cada columna
		int aNum = 4; // "#"
		int aResultado = 12; // "ABANDONADA" = 10 chars → 12 con margen
		int aMovs = 13; // "Movimientos" = 11 chars → 13 con margen
		int aTiempo = 15; // "10 min 59 seg" = 13 chars → 15 con margen

		String sep = separador(aNum, aResultado, aMovs, aTiempo);

		System.out.println();
		System.out.println(sep);
		System.out.println(fila(aNum, aResultado, aMovs, aTiempo, "#", "Resultado", "Movimientos", "Tiempo"));
		System.out.println(sep);

		int num = 1;
		boolean hayFilas = resultadoHistorial.next();

		while (hayFilas) {
			// true = ganada, false = abandonada
			String resultado = resultadoHistorial.getBoolean("ganada") ? "GANADA" : "ABANDONADA";
			String movs = String.valueOf(resultadoHistorial.getInt("movimientos"));
			String tiempo = formatearTiempo(resultadoHistorial.getInt("tiempo_segundos"));

			System.out.println(fila(aNum, aResultado, aMovs, aTiempo, String.valueOf(num), resultado, movs, tiempo));

			num++;
			hayFilas = resultadoHistorial.next();
		}

		System.out.println(sep);

		if (num == 1) {
			System.out.println("| Todavia no tienes partidas registradas.          |");
			System.out.println(sep);
		}
		System.out.println("========================\n");

		resultadoHistorial.close();
		consultaHistorial.close();
	}

	// ======== MÉTODOS AUXILIARES ========

	// Construye la línea separadora con los anchos de cada columna
	// Ejemplo: +----+------------------+-----------+
	private String separador(int... anchos) {
		String linea = "+";
		int i = 0;
		while (i < anchos.length) {
			int j = 0;
			while (j < anchos[i]) {
				linea += "-";
				j++;
			}
			linea += "+";
			i++;
		}
		return linea;
	}

	// Construye una fila de datos con cada valor ajustado al ancho de su columna
	// Usamos %-Ns para alinear a la izquierda con N caracteres de ancho
	// Los anchos recibidos incluyen el espacio de margen
	// Ejemplo: "| 1 | pepito | 3 |"
	private String fila(int a1, int a2, int a3, int a4, String v1, String v2, String v3, String v4) {
		return String.format(
				"| %-" + (a1 - 2) + "s | %-" + (a2 - 2) + "s | %-" + (a3 - 2) + "s | %-" + (a4 - 2) + "s |", v1, v2, v3,
				v4);
	}

	// Sobrecarga para el ranking que tiene 5 columnas
	private String fila(int a1, int a2, int a3, int a4, int a5, String v1, String v2, String v3, String v4, String v5) {
		return String.format("| %-" + (a1 - 2) + "s | %-" + (a2 - 2) + "s | %-" + (a3 - 2) + "s | %-" + (a4 - 2)
				+ "s | %-" + (a5 - 2) + "s |", v1, v2, v3, v4, v5);
	}

	// Convierte segundos en bruto al formato "X min Y seg" para mostrar al jugador
	// En la BBDD guardamos segundos para poder ordenar numericamente en el ranking
	// Ejemplo: 125 → "2 min 5 seg" | 45 → "45 seg"
	private String formatearTiempo(int segundos) {
		int minutos = segundos / 60;
		int segsRestantes = segundos % 60;

		if (minutos == 0) {
			return segsRestantes + " seg";
		}

		return minutos + " min " + segsRestantes + " seg";
	}
}