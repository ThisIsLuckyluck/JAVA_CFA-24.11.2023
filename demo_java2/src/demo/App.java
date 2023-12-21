package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Lancement du script de connexion");

        String jdbcurl = "jdbc:h2:tcp://localhost/~/test";
		String login = "root";
		String mdp = "root";

        try (Connection connection = DriverManager.getConnection(jdbcurl, login, mdp)) {
            System.out.println("Connexion réussie");

            CommandeDao commandeDao = new PersonneDaoImpl();
            ArticleDao articleDao = new PersonneDaoImpl();
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
                        displayAllArticles(articleDao, connection);
                        break;
                    case 7:
                        createNewArticle(articleDao, scanner, connection);
                        break;
                    case 8:
                        deleteArticleById(articleDao, scanner, connection);
                        break;
                    case 9:
                        updateArticle(articleDao, scanner, connection);
                        break;
                    case 10:
                        displayAllCommandes(commandeDao, connection);
                        break;
                    case 11:
                        createNewCommande(commandeDao, scanner, connection);
                        break;
                    case 12:
                        deleteCommandeById(commandeDao, scanner, connection);
                        break;
                    case 13:
                        updateCommande(commandeDao, scanner, connection);
                        break;
                    case 14:
                        System.out.println("Fin du programme");
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
    // Interface utilisateur
    private static void displayMenu() {
        System.out.println("Choisissez une action :");
        System.out.println("1. Tout afficher");
        System.out.println("2. Chercher une personne via son ID");
        System.out.println("3. Insérer une personne dans la bdd");
        System.out.println("4. Supprimer une personne de la bdd");
        System.out.println("5. Mettre à jour une personne");
        System.out.println("------------------------");
        System.out.println("6. Chercher un article via son ID");
        System.out.println("7. Insérer un article dans la bdd");
        System.out.println("8. Supprimer un article de la bdd");
        System.out.println("9. Mettre à jour un article");
        System.out.println("------------------------");
        System.out.println("10. Chercher une commande via son ID");
        System.out.println("11. Insérer une commande dans la bdd");
        System.out.println("12. Supprimer une une commande de la bdd");
        System.out.println("13. Mettre à jour une une commande");
        System.out.println("14. Fin");
    }

    //Personne SECTION
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
        Personne personne = personneDao.getPersonneById(idToDisplay, connection);

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
        personneDao.deletePersonneById(idToDelete, connection);
    }

    private static void updatePerson(PersonneDao personneDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la personne que vous souhaitez mettre à jour : ");
        int idToUpdate = scanner.nextInt();
        Personne personneToUpdate = personneDao.getPersonneById(idToUpdate, connection);

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

    //---------------------------------------------------------------------------------
    
    //Article SECTION

    public static void displayAllArticles(ArticleDao articleDao, Connection connection) {
        List<Article> articles = articleDao.getAllArticles(connection);

        if (!articles.isEmpty()) {
            System.out.println("Liste de tous les articles :");
            for (Article article : articles) {
                System.out.println("ID: " + article.getId() + ", Nom: " + article.getNom() + ", Prix: " + article.getPrix());
            }
        } else {
            System.out.println("Aucun article trouvé.");
        }
    }

    public static void createNewArticle(ArticleDao articleDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID du nouvel article : ");
        int id = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Entrez le nom du nouvel article : ");
        String nom = scanner.nextLine();

        System.out.println("Entrez le prix du nouvel article : ");
        int prix = scanner.nextInt();

        Article nouvelArticle = new Article();
        nouvelArticle.setId(id);
        nouvelArticle.setNom(nom);
        nouvelArticle.setPrix(prix);

        articleDao.createArticle(nouvelArticle, connection);
    }
    public static void deleteArticleById(ArticleDao articleDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de l'article que vous souhaitez supprimer : ");
        int idToDelete = scanner.nextInt();
        articleDao.deleteArticleById(idToDelete, connection);
    }

    public static void updateArticle(ArticleDao articleDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de l'article que vous souhaitez mettre à jour : ");
        int idToUpdate = scanner.nextInt();
        Article articleToUpdate = articleDao.getArticleById(idToUpdate, connection);

        if (articleToUpdate != null) {
            System.out.println("Entrez le nouveau nom : ");
            String newNom = scanner.next();

            System.out.println("Entrez le nouveau prix : ");
            int newPrix = scanner.nextInt();

            articleToUpdate.setNom(newNom);
            articleToUpdate.setPrix(newPrix);

            articleDao.updateArticle(articleToUpdate, connection);
        } else {
            System.out.println("Aucun article trouvé avec l'ID " + idToUpdate);
        }
    }
    //---------------------------------------------------------------------------------
    //Commande SECTION
    public static void displayAllCommandes(CommandeDao commandeDao, Connection connection) {
        List<Commande> commandes = commandeDao.getAllCommandes(connection);

        if (!commandes.isEmpty()) {
            System.out.println("Liste de toutes les commandes :");
            for (Commande commande : commandes) {
                System.out.println("ID: " + commande.getId() + ", Nom: " + commande.getNom() + ", Prix: " + commande.getPrix());
            }
        } else {
            System.out.println("Aucune commande trouvée.");
        }
    }

    public static void createNewCommande(CommandeDao commandeDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la nouvelle commande : ");
        int id = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Entrez le nom de la nouvelle commande : ");
        String nom = scanner.nextLine();

        System.out.println("Entrez le prix de la nouvelle commande : ");
        int prix = scanner.nextInt();

        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setId(id);
        nouvelleCommande.setNom(nom);
        nouvelleCommande.setPrix(prix);

        commandeDao.createCommande(nouvelleCommande, connection);
    }

    public static void deleteCommandeById(CommandeDao commandeDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la commande que vous souhaitez supprimer : ");
        int idToDelete = scanner.nextInt();
        commandeDao.deleteCommandeById(idToDelete, connection);
    }

    public static void updateCommande(CommandeDao commandeDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la commande que vous souhaitez mettre à jour : ");
        int idToUpdate = scanner.nextInt();
        Commande commandeToUpdate = commandeDao.getCommandeById(idToUpdate, connection);

        if (commandeToUpdate != null) {
            System.out.println("Entrez le nouveau nom : ");
            String newNom = scanner.next();

            System.out.println("Entrez le nouveau prix : ");
            int newPrix = scanner.nextInt();

            commandeToUpdate.setNom(newNom);
            commandeToUpdate.setPrix(newPrix);

            commandeDao.updateCommande(commandeToUpdate, connection);
        } else {
            System.out.println("Aucune commande trouvée avec l'ID " + idToUpdate);
        }
    }

    public static void displayCommandeById(CommandeDao commandeDao, Scanner scanner, Connection connection) {
        System.out.println("Entrez l'ID de la commande que vous souhaitez afficher : ");
        int idToDisplay = scanner.nextInt();
        Commande commande = commandeDao.getCommandeById(idToDisplay, connection);

        if (commande != null) {
            System.out.println("Détails de la commande avec l'ID " + idToDisplay + ":");
            System.out.println("Nom: " + commande.getNom() + ", Prix: " + commande.getPrix());
        } else {
            System.out.println("Aucune commande trouvée avec l'ID " + idToDisplay);
        }
    }
}
