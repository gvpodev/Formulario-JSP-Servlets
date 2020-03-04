package connection;

import java.sql.Connection;
import java.sql.DriverManager;

// Conexão com o banco de dados
public class SingleConnection {
	private static String banco = "jdbc:postgresql://localhost:5433/db-projeto-jsp?autoReconnect=true";
	private static String password = "q1w2e3";
	private static String user = "postgres";
	private static Connection connection = null;
	
	// Garante a execucao do conectar sempre que a classe for invocada.
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}
		}catch(Exception e) {
			throw new RuntimeException("Erro ao conectar ao banco de dados.");
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
