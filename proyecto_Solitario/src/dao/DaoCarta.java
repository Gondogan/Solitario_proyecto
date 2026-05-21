package dao;

import model.Carta;
import java.sql.*;

public class DaoCarta {
	
	private Connection conn = null;
	
	public static DaoCarta instance = null;
	
	public static DaoCarta getInstance() throws SQLException {
		
		if (instance == null) {
			
			instance = new DaoCarta();
		}
		
		return instance;
	}
	
	public DaoCarta() throws SQLException{
		
		conn = DbConnection.getConnection();
	}
	
	public void insertCarta(Carta cartaIn) throws SQLException{
		
		String insertSql = "INSERT INTO cartas (valor, palo, color) VALUES (?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(insertSql);
		
		ps.setInt(1, cartaIn.getValor());
		ps.setString(2, cartaIn.getPalo().name());  //Esto es para poder setear el enum porque no acepta enum, tenemos que ponerlo en String
		ps.setString(3, cartaIn.getColor().name());
		
		int recordsInserted = ps.executeUpdate();
		
		if(recordsInserted > 0) {
			System.out.println("Carta insertada correctamente");
		}else {
			System.out.println("No se ha podido insertar la carta");
		}
		ps.close();
	}

}
