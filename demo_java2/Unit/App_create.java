package demo;
import java.sql.*;
import java.util.Scanner;

public class App_create {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        int id = 0;
        String name = "";
        int age = 0;

        System.out.println("Lancement du script de connexion");

        String jdbcurl = "jdbc:h2:tcp://localhost/db_name";
        String login = "root";
        String mdp = "root";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcurl, login, mdp);
            System.out.println("Connexion réussie");

            System.out.println("Quel id souhaitez-vous attribuer au nouvel utilisateur ?");
            id = clavier.nextInt();
            clavier.nextLine();

            System.out.println("Quel nom souhaitez-vous attribuer au nouvel utilisateur ?");
            name = clavier.nextLine();

            System.out.println("Quel âge souhaitez-vous attribuer au nouvel utilisateur ?");
            age = clavier.nextInt();

			//exec
            String insertQuery = "INSERT INTO personne (ID, NAME, AGE) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, age);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Insertion réussie pour l'utilisateur avec ID : " + id);
                } else {
                    System.out.println("Échec de l'insertion.");
                }
            }
			//info
            String selectQuery = "SELECT * FROM personne WHERE ID = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setInt(1, id);
                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    int userId = resultSet.getInt("ID");
                    String userName = resultSet.getString("NAME");
                    int userAge = resultSet.getInt("AGE");

                    System.out.println("Informations du nouvel utilisateur :");
                    System.out.println("ID : " + userId);
                    System.out.println("Nom : " + userName);
                    System.out.println("Âge : " + userAge);
                }
            }

        } catch (SQLException e) {
            System.out.println("Connexion échouée");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                clavier.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
