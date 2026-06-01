package model;

public class Jugador {

	private int Id;
	private String nombreUsuario;
	private String password;

	// ==================== CONSTRUCTORES ===============================

	public Jugador() {
	}

	public Jugador(String nameJugador, String password) {

		this.nombreUsuario = nameJugador;
		this.password = password;
	}

	// =================== GETTERS Y SETTERS ===========================

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	// ======================== toString =====================================

	public String toString() {
		return "Jugador: " + nombreUsuario;
	}

}
