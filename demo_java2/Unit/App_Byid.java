package demo;
import java.sql.*;
import java.util.Scanner;

public class App_Byid {
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        int id;
		
        System.out.println("Lancement du script de connexion");
		
        String jdbcurl = "jdbc:h2:tcp://localhost/db_name";
        String login = "root";
        String mdp = "root";
		
        Statement stmt = null;
        Connection connection = null;
		
        try {
            connection = DriverManager.getConnection(jdbcurl, login, mdp);
            System.out.println("Connexion réussie");

            stmt = connection.createStatement();
            String request = "SELECT * FROM personne WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(request);

            System.out.println("Tapez l'id que vous souhaitez consulter :");

            while (true) {
                try {
                    id = clavier.nextInt();
                    preparedStatement.setInt(1, id); 
                    break;
                } catch (java.util.InputMismatchException e) {
                    clavier.nextLine();
                    System.out.println("Uniquement des chiffres !");
                    System.out.println("Tapez à nouveau l'id que vous souhaitez consulter :");
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") );
				System.out.println(", Nom: " + resultSet.getString("Name") );
				System.out.println(", Age: " + resultSet.getInt("AGE") );
            }

        } catch (SQLException e) {
            System.out.println("Connexion échouée");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
                clavier.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
