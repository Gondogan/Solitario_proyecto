package dao;

import model.Jugador;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DaoJugador {

	// Conexión a la base de datos, la obtenemos de DbConnection
	private Connection conn = null;

	// Instancia única del DAO, siguiendo el patrón Singleton
	// Solo existirá un objeto DaoJugador durante toda la ejecución
	public static DaoJugador instance = null;

	// Método para obtener la instancia única
	// Si no existe la creamos, si ya existe la devolvemos
	public static DaoJugador getInstance() throws SQLException {

		if (instance == null) {
			instance = new DaoJugador();
		}

		return instance;
	}

	// Constructor: obtiene la conexión compartida de DbConnection
	public DaoJugador() throws SQLException {
		conn = DbConnection.getConnection();
	}

	// ======== MÉTODOS ========

	// Convierte la contraseña a SHA-256 antes de guardarla o compararla
	// SHA-256 es un algoritmo de cifrado de un solo sentido: no se puede revertir
	// Así si alguien accede a la BBDD solo verá una cadena de 64 caracteres, nunca
	// la contraseña real
	// Ejemplo: "1234" → "03ac674216f3e15c761ee1a5e255f067..."

	private String hashPassword(String password) throws NoSuchAlgorithmException {

		// MessageDigest es la clase de Java que aplica el algoritmo SHA-256
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		// digest() convierte el texto a un array de bytes cifrados
		byte[] hash = md.digest(password.getBytes());

		// Convertimos cada byte a su equivalente en hexadecimal de 2 dígitos
		// %02x significa: formato hexadecimal con mínimo 2 caracteres
		// El resultado es siempre una cadena de exactamente 64 caracteres
		StringBuilder sb = new StringBuilder();
		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	// Comprueba si un nombre de usuario ya existe en la BBDD
	// Lo usamos para saber si el jugador está registrado o no
	// Devuelve true si ya existe, false si no existe
	public boolean existeUsuario(String nombreUsuario) throws SQLException {

		String selectUsuario = "SELECT id FROM jugadores WHERE nombre_usuario = ?";

		PreparedStatement checkUsuario = conn.prepareStatement(selectUsuario);
		checkUsuario.setString(1, nombreUsuario);

		ResultSet usuarioEncontrado = checkUsuario.executeQuery();

		// usuarioEncontrado.next() devuelve true si hay alguna fila,
		// es decir, si el usuario ya existe en la BBDD
		boolean existe = usuarioEncontrado.next();

		usuarioEncontrado.close();
		checkUsuario.close();

		return existe;
	}

	// Intenta hacer login con el usuario y contraseña introducidos
	// Primero comprueba si el usuario existe
	// Si existe, comprueba si la contraseña es correcta
	// Si no existe, avisa de que el usuario no está registrado
	// Devuelve el objeto Jugador si todo es correcto, o null si algo falla
	public Jugador login(String nombreUsuario, String password) throws SQLException, NoSuchAlgorithmException {

		// Primero comprobamos si el usuario existe en la BBDD
		// Así podemos dar un mensaje de error más concreto al jugador
		if (!existeUsuario(nombreUsuario)) {
			System.out.println("El usuario '" + nombreUsuario + "' no existe.");
			return null;
		}

		// El usuario existe, ahora comprobamos si la contraseña es correcta
		// Buscamos una fila donde coincidan usuario Y contraseña hasheada
		String selectLogin = "SELECT * FROM jugadores WHERE nombre_usuario = ? AND password = ?";

		PreparedStatement checkLogin = conn.prepareStatement(selectLogin);
		checkLogin.setString(1, nombreUsuario);

		// Hasheamos lo que escribe el usuario para compararlo con el hash de la BBDD
		checkLogin.setString(2, hashPassword(password));

		ResultSet jugadorEncontrado = checkLogin.executeQuery();

		Jugador jugador = null;

		if (jugadorEncontrado.next()) {
			// Usuario y contraseña correctos, construimos el objeto Jugador
			jugador = new Jugador();
			jugador.setId(jugadorEncontrado.getInt("id"));
			jugador.setNombreUsuario(jugadorEncontrado.getString("nombre_usuario"));
			// No guardamos la contraseña en el objeto por seguridad,
			// una vez logueado ya no la necesitamos
			System.out.println("Bienvenido, " + jugador.getNombreUsuario() + "!");
		} else {
			// El usuario existe pero la contraseña no coincide
			System.out.println("Contraseña incorrecta.");
		}

		jugadorEncontrado.close();
		checkLogin.close();

		return jugador;
	}

	// Registra un jugador nuevo en la BBDD
	// Solo se llama cuando el jugador ha confirmado que quiere registrarse
	// Devuelve true si se ha insertado correctamente, false si no
	public boolean registrarJugador(Jugador jugador) throws SQLException, NoSuchAlgorithmException {

		String insertJugador = "INSERT INTO jugadores (nombre_usuario, password) VALUES (?, ?)";

		PreparedStatement nuevoJugador = conn.prepareStatement(insertJugador);
		nuevoJugador.setString(1, jugador.getNombreUsuario());

		// Guardamos el hash de la contraseña, nunca la contraseña en texto plano
		nuevoJugador.setString(2, hashPassword(jugador.getPassword()));

		int jugadorInsertado = nuevoJugador.executeUpdate();
		nuevoJugador.close();

		if (jugadorInsertado > 0) {
			System.out.println("Jugador registrado correctamente. Ya puedes iniciar sesión.");
			return true;
		} else {
			System.out.println("No se ha podido registrar el jugador.");
			return false;
		}
	}
}