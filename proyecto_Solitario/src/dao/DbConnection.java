package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

	// Pedir la conexión de BBDD
	
	public static Connection instance = null;
	
	public static final String JDBC_BBDD_URL = "jdbc:mysql://localhost:3306/ProyectoSolitario";
	
	public static Connection getConnection() throws SQLException {
		
		if (instance == null) {
			
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "");
			
			instance = DriverManager.getConnection(JDBC_BBDD_URL,props);
		}
		
		return instance;
	}
	
	
}
