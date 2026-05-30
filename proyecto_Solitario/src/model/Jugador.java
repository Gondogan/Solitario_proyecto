package model;

public class Jugador {

	private String nombreUsuario;
	private String password;

	//==================== CONSTRUCTORES ===============================
	
	public Jugador() {
	}
	
	public Jugador(String nameJugador, String password) {
		
		this.nombreUsuario = nameJugador;
		this.password = password;
	}


	//=================== GETTERS Y SETTERS ===========================
	
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
