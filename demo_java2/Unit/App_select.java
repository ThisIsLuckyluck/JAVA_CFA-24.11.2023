package demo;
import java.sql.*;

public class App_select {

	public static void main(String[] args) {
		
		System.out.println("Lancement du script de connection");
		
		String jdbcurl = "jdbc:h2:tcp://localhost/~/test";
		String login = "root";
		String mdp = "root";
		
		Statement stmt =null;
		Connection connection =null;
		
		try {
		connection= DriverManager.getConnection(jdbcurl, login, mdp);
		System.out.println("Connection succes");
		
		 stmt = connection.createStatement();
		String request = "select * from personne";
		ResultSet resultSet =stmt.executeQuery(request);
		
			while (resultSet.next()) {
				System.out.println("ID: " + resultSet.getInt("id") );
				System.out.println(", Nom: " + resultSet.getString("Name") );
				System.out.println(", Age: " + resultSet.getInt("AGE") );

			}
		
		
		} catch (SQLException e) {
				System.out.println("Connexion échoué");
				e.printStackTrace();
			}
		
		finally {
			try {
				if (stmt !=null) stmt.close();
				if (connection !=null) connection.close();
			}catch(Exception e) {
			}
		}
		
	}

}
