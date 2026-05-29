package dao;

import model.Mazo;
import model.Carta;
import model.Palo;
import model.ColorCarta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoMazo {
    
	private Connection conn = null; 
	
	public static DaoMazo instance = null; 
	
	public static DaoMazo getInstance() throws SQLException {
		
		if (instance == null) {
			instance = new DaoMazo();
		}
		
		return instance;
	}
	
	public DaoMazo() throws SQLException {
		
		conn = DbConnection.getConnection();
		
	}

	// Cambiamos DaoMazo por Mazo, y lanzamos la excepción hacia arriba
	public Mazo selectAll() throws SQLException {
		
		// Creamos el Mazo provisional para almacenar las cartas de la BBDD
		Mazo mazoCompleto = new Mazo();  
		
		String selectAllData = "SELECT * FROM cartas"; // Guardo la query en una variable 
		
		Carta cartaActual;
		Statement statementSelect = conn.createStatement(); 
		
		ResultSet resultData = statementSelect.executeQuery(selectAllData);
		
		while(resultData.next()) { // Mientras haya un siguiente elemento sigue recorriendo
			
			// Accedemos por medio del nombre de la columna
			int valor = resultData.getInt("valor"); // Usamos getInt porque el valor es un int
			
			// Extraemos los textos de la BBDD y los convertimos directamente a nuestros Enum
			Palo palo = Palo.valueOf(resultData.getString("palo"));
			ColorCarta color = ColorCarta.valueOf(resultData.getString("color"));
			
			// Creamos la carta con la información sacada
			cartaActual = new Carta(valor, palo, color);
			
			// Añadimos la carta a nuestro mazo
			mazoCompleto.agregarCarta(cartaActual);	
		
		}
		
		statementSelect.close(); // Cerramos la consulta 
		
		// Retornamos el mazo provisional donde se han guardado todas las cartas
		return mazoCompleto;
		
	}
	
}