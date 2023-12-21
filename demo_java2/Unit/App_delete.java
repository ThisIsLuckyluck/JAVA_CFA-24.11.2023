package demo;
import java.sql.*;
import java.util.Scanner;

public class App_delete {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        String nameToDelete = "";

        System.out.println("Lancement du script de connexion");

        String jdbcurl = "jdbc:h2:tcp://localhost/db_name";
        String login = "root";
        String mdp = "root";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcurl, login, mdp);
            System.out.println("Connexion réussie");

            System.out.println("Quel utilisateur souhaitez-vous supprimer ?");
            nameToDelete = clavier.nextLine();

            String deleteQuery = "DELETE FROM personne WHERE NAME = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, nameToDelete);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Suppression réussie pour l'utilisateur " + nameToDelete);
                } else {
                    System.out.println("Aucun utilisateur avec le nom : " + nameToDelete + " trouvé.");
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
