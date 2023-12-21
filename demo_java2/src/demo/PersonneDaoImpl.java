package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneDaoImpl implements PersonneDao, ArticleDao, CommandeDao{

    private static final String SELECT_ALL_QUERY_PERSONNE = "SELECT * FROM PERSONNE";
    private static final String SELECT_BY_ID_QUERY_PERSONNE = "SELECT * FROM PERSONNE WHERE ID = ?";
    private static final String INSERT_QUERY_PERSONNE = "INSERT INTO PERSONNE (ID, NAME, AGE) VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY_PERSONNE = "DELETE FROM PERSONNE WHERE ID = ?";
    private static final String UPDATE_QUERY_PERSONNE = "UPDATE PERSONNE SET NAME = ?, AGE = ? WHERE ID = ?";
    //--------------------------------------------------------------------------------------
    private static final String SELECT_ALL_QUERY_ARTICLE = "SELECT * FROM ARTICLE";
    private static final String SELECT_BY_ID_QUERY_ARTICLE = "SELECT * FROM ARTICLE WHERE ID = ?";
    private static final String INSERT_QUERY_ARTICLE = "INSERT INTO ARTICLE (ID, NAME, PRICE) VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY_ARTICLE = "DELETE FROM ARTICLE WHERE ID = ?";
    private static final String UPDATE_QUERY_ARTICLE = "UPDATE ARTICLE SET NAME = ?, PRICE = ? WHERE ID = ?";
    //--------------------------------------------------------------------------------------
    private static final String SELECT_ALL_QUERY_COMMANDE = "SELECT * FROM COMMANDE";
    private static final String SELECT_BY_ID_QUERY_COMMANDE = "SELECT * FROM COMMANDE WHERE ID = ?";
    private static final String INSERT_QUERY_COMMANDE = "INSERT INTO COMMANDE (ID, NAME, PRICE) VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY_COMMANDE = "DELETE FROM COMMANDE WHERE ID = ?";
    private static final String UPDATE_QUERY_COMMANDE = "UPDATE COMMANDE SET NAME = ?, PRICE = ? WHERE ID = ?";
    //--------------------------------------------------------------------------------------

    //Partie Personne

    //SELECT * FROM PERSONNE
    @Override 
    public List<Personne> getAllPersons(Connection connection) {
        List<Personne> personnes = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY_PERSONNE)) {

            while (resultSet.next()) {
                Personne personne = mapResultSetToPersonne(resultSet);
                personnes.add(personne);
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération de toutes les personnes");
        }

        return personnes;
    }
    //--------------------------------------------------------------------------------------

    //SELECT FROM PERSONNE WHERE ID = ?
    @Override
    public Personne getPersonneById(int id, Connection connection) {
        Personne personne = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY_PERSONNE)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    personne = mapResultSetToPersonne(resultSet);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération de la personne par ID");
        }

        return personne;
    }
    //--------------------------------------------------------------------------------------

    //INSERT INTO PERSONNE (ID, NAME, AGE) VALUES (?, ?, ?)
    @Override
    public void createPersonne(Personne personne, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_PERSONNE)) {
            preparedStatement.setInt(1, personne.getId());
            preparedStatement.setString(2, personne.getNom());
            preparedStatement.setInt(3, personne.getAge());

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Création de la personne", "Échec de la création de la personne");

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la création de la personne");
        }
    }
    //--------------------------------------------------------------------------------------

    //DELETE FROM PERSONNE WHERE ID = ?
    @Override
    public void deletePersonneById(int id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY_PERSONNE)) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Suppression de la personne", "Aucune personne trouvée avec l'ID " + id);

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la suppression de la personne");
        }
    }
    //--------------------------------------------------------------------------------------

    //UPDATE PERSONNE SET NAME = ?, AGE = ? WHERE ID = ?
    @Override
    public void updatePersonne(Personne personne, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY_PERSONNE)) {
            preparedStatement.setString(1, personne.getNom());
            preparedStatement.setInt(2, personne.getAge());
            preparedStatement.setInt(3, personne.getId());

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Mise à jour de la personne", "Aucune personne trouvée avec l'ID " + personne.getId());

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la mise à jour de la personne");
        }
    }
    //--------------------------------------------------------------------------------------

    //Partie Article

    //SELECT * FROM ARTICLE
    @Override
    public List<Article> getAllArticles(Connection connection) {
        List<Article> articles = new ArrayList<>();
    
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY_ARTICLE)) {
    
            while (resultSet.next()) {
                Article article = mapResultSetToArticle(resultSet);
                articles.add(article);
            }
    
        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération de toutes les articles");
        }
    
        return articles;
    }
    //--------------------------------------------------------------------------------------

    //SELECT BY ID FROM ARTICLE
    @Override
    public Article getArticleById(int id, Connection connection) {
        Article article = null;
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY_ARTICLE)) {
            preparedStatement.setInt(1, id);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    article = mapResultSetToArticle(resultSet);
                }
            }
    
        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération de la article par ID");
        }
    
        return article;
    }
    //--------------------------------------------------------------------------------------

    //INSERT INTO ARTICLE (ID, NAME, AGE) VALUES (?, ?, ?)
    @Override
    public void createArticle(Article article, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_ARTICLE)) {
            preparedStatement.setInt(1, article.getId());
            preparedStatement.setString(2, article.getNom());
            preparedStatement.setInt(3, article.getPrix());
    
            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Création de la article", "Échec de la création de la article");
    
        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la création de la article");
        }
    }
    //--------------------------------------------------------------------------------------

    //DELETE FROM ARTICLE WHERE ID = ?
    @Override
    public void deleteArticleById(int id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY_ARTICLE)) {
            preparedStatement.setInt(1, id);
    
            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Suppression de la article", "Aucune article trouvée avec l'ID " + id);
    
        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la suppression de la article");
        }
    }
    //--------------------------------------------------------------------------------------

    //UPDATE ARTICLE SET NAME = ?, AGE = ? WHERE ID = ?
    @Override
    public void updateArticle(Article article, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY_ARTICLE)) {
            preparedStatement.setString(1, article.getNom());
            preparedStatement.setInt(2, article.getPrix());
            preparedStatement.setInt(3, article.getId());
    
            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Mise à jour de la article", "Aucune article trouvée avec l'ID " + article.getId());
    
        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la mise à jour de la article");
        }
    }
    
    //--------------------------------------------------------------------------------------

    //Partie Commande
    public List<Commande> getAllCommandes(Connection connection) {
        List<Commande> commandes = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY_COMMANDE)) {

            while (resultSet.next()) {
                Commande commande = mapResultSetToCommande(resultSet);
                commandes.add(commande);
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération de toutes les commandes");
        }

        return commandes;
    }

    public Commande getCommandeById(int id, Connection connection) {
        Commande commande = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY_COMMANDE)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    commande = mapResultSetToCommande(resultSet);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la récupération de la commande par ID");
        }

        return commande;
    }

    public void createCommande(Commande commande, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_COMMANDE)) {
            preparedStatement.setInt(1, commande.getId());
            preparedStatement.setString(2, commande.getNom());
            preparedStatement.setInt(3, commande.getPrix());

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Création de la commande", "Échec de la création de la commande");

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la création de la commande");
        }
    }

    public void deleteCommandeById(int id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY_COMMANDE)) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Suppression de la commande", "Aucune commande trouvée avec l'ID " + id);

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la suppression de la commande");
        }
    }

    public void updateCommande(Commande commande, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY_COMMANDE)) {
            preparedStatement.setString(1, commande.getNom());
            preparedStatement.setInt(2, commande.getPrix());
            preparedStatement.setInt(3, commande.getId());

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Mise à jour de la commande", "Aucune commande trouvée avec l'ID " + commande.getId());

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la mise à jour de la commande");
        }
    }

    private Personne mapResultSetToPersonne(ResultSet resultSet) throws SQLException {
        Personne personne = new Personne();
        personne.setId(resultSet.getInt("ID"));
        personne.setNom(resultSet.getString("NAME"));
        personne.setAge(resultSet.getInt("AGE"));
        return personne;
    }

    private void handleSQLException(SQLException e, String message) {
        System.out.println(message);
        e.printStackTrace();
    }

    private void handleAffectedRows(int affectedRows, String successMessage, String failureMessage) {
        if (affectedRows == 0) {
            System.out.println(failureMessage);
        } else {
            System.out.println(successMessage);
        }
    }

    private Article mapResultSetToArticle(ResultSet resultSet) throws SQLException {
        Article article = new Article();
        article.setId(resultSet.getInt("id"));
        article.setNom(resultSet.getString("nom"));
        article.setPrix(resultSet.getInt("prix"));
        return article;
    }

    private Commande mapResultSetToCommande(ResultSet resultSet) throws SQLException {
        Commande commande = new Commande();
        commande.setId(resultSet.getInt("id"));
        commande.setNom(resultSet.getString("nom"));
        commande.setPrix(resultSet.getInt("prix"));
        return commande;
    }
}
