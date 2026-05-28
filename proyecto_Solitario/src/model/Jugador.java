package model;

public class Jugador {

	private int idJugador;
	private String nombreUsuario;
	private String password;

	//==================== CONSTRUCTORES ===============================
	
	public Jugador() {
	}
	
	public Jugador(String nameJugador, String password) {
		
		this.nombreUsuario = nameJugador;
		this.password = password;
	}

	public Jugador(int idJugador, String nameJugador, String password) {
		
		this.idJugador = idJugador;
		this.nombreUsuario = nameJugador;
		this.password = password;
	}

	//=================== GETTERS Y SETTERS ===========================
	
	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}
	
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	//======================== toString =====================================
	
	public String toString() {
		return "Jugador: " + nombreUsuario;
	}
	
	
}
