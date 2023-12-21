package demo;
import java.sql.*;
import java.util.Scanner;

public class App_update {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        String nameToupdate = "";

        System.out.println("Lancement du script de connexion");

        String jdbcurl = "jdbc:h2:tcp://localhost/~/test";
        String login = "root";
        String mdp = "root";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcurl, login, mdp);
            System.out.println("Connexion réussie");

            System.out.println("Quel utilisateur souhaitez-vous mofifier ?");
            nameToupdate = clavier.nextLine();

            String updateQuery = "UPDATE PERSONNE SET NAME = ?, AGE = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, nameToupdate);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Information mis a jour pour " + nameToupdate);
                } else {
                    System.out.println("Aucun utilisateur avec le nom : " + nameToupdate + " trouvé.");
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
