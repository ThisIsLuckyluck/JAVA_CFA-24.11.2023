package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Lancement du script de connexion");

        String jdbcurl = "url/using/h2/localhost";
        String login = "your_log";
        String mdp = "your_passwd";

        try (Connection connection = DriverManager.getConnection(jdbcurl, login, mdp)) {
            System.out.println("Connexion réussie");

            PersonneDao personneDao = new PersonneDaoImpl();
            Scanner scanner = new Scanner(System.in);

            int choice = 0;

            do {
                displayMenu();
            
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Saisie incorrecte. Veuillez entrer un nombre entier.");
                    scanner.next();
                    continue;
                }
            
                switch (choice) {
                    case 1:
                        displayAllPersons(personneDao, connection);
                        break;
                    case 2:
                        displayPersonById(personneDao, scanner, connection);
                        break;
                    case 3:
                        createNewPerson(personneDao, scanner, connection);
                        break;
                    case 4:
                        deletePersonById(personneDao, scanner, connection);
                        break;
                    case 5:
                        updatePerson(personneDao, scanner, connection);
                        break;
                    case 6:
                        System.out.println("Fin du programme.");
                        break;
                    default:
                        System.out.println("Choix non valide. Veuillez réessayer.");
                }

            } while (choice != 6);

        } catch (SQLException e) {
            System.out.println("Connexion échouée");
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("Choisissez une action :");
        System.out.println("1. Tout afficher");
        System.out.println("2. Chercher une personne via son ID");
        System.out.println("3. Insérer une personne dans la bdd");
        System.out.println("4. Supprimer une personne de la bdd");
        System.out.println("5. Mettre à jour une personne");
        System.out.println("6. Fin");
    }

    private static void displayAllPersons(PersonneDao personneDao, Connection connection) {
        List<Personne> personnes = personneDao.getAllPersons(connection);

        if (!personnes.isEmpty()) {
            System.out.println("Liste de toutes les personnes :");
            for (Personne personne : personnes) {
                System.out.println("ID: " + personne.getId() + ", Nom: " + personne.getNom() + ", Age: " + personne.getAge());
            }
        } else {
            System.out.println("Aucune personne trouvée.");
        }
    }

    private static void displayPersonById(PersonneDao personneDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la personne que vous souhaitez afficher : ");
        int idToDisplay = scanner.nextInt();
        Personne personne = personneDao.getById(idToDisplay, connection);

        if (personne != null) {
            System.out.println("Détails de la personne avec l'ID " + idToDisplay + ":");
            System.out.println("Nom: " + personne.getNom() + ", Age: " + personne.getAge());
        } else {
            System.out.println("Aucune personne trouvée avec l'ID " + idToDisplay);
        }
    }

    private static void createNewPerson(PersonneDao personneDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la nouvelle personne : ");
        int id = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Entrez le nom de la nouvelle personne : ");
        String nom = scanner.nextLine();

        System.out.println("Entrez l'âge de la nouvelle personne : ");
        int age = scanner.nextInt();

        Personne nouvellePersonne = new Personne();
        nouvellePersonne.setId(id);
        nouvellePersonne.setNom(nom);
        nouvellePersonne.setAge(age);

        personneDao.createPersonne(nouvellePersonne, connection);
    }

    private static void deletePersonById(PersonneDao personneDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la personne que vous souhaitez supprimer : ");
        int idToDelete = scanner.nextInt();
        personneDao.deleteById(idToDelete, connection);
    }

    private static void updatePerson(PersonneDao personneDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la personne que vous souhaitez mettre à jour : ");
        int idToUpdate = scanner.nextInt();
        Personne personneToUpdate = personneDao.getById(idToUpdate, connection);

        if (personneToUpdate != null) {
            System.out.println("Entrez le nouveau nom : ");
            String newNom = scanner.next();

            System.out.println("Entrez l'âge de " + newNom);
            int newAge = scanner.nextInt();

            personneToUpdate.setNom(newNom);
            personneToUpdate.setAge(newAge);

            personneDao.updatePersonne(personneToUpdate, connection);
        } else {
            System.out.println("Aucune personne trouvée avec l'ID " + idToUpdate);
        }
    }
}
