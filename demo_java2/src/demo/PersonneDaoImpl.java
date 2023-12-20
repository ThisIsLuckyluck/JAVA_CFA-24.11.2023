package demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonneDaoImpl implements PersonneDao {

    private static final String SELECT_ALL_QUERY_PERSONNE = "SELECT * FROM PERSONNE";
    private static final String SELECT_BY_ID_QUERY_PERSONNE = "SELECT * FROM PERSONNE WHERE ID = ?";
    private static final String INSERT_QUERY_PERSONNE = "INSERT INTO PERSONNE (ID, NAME, AGE) VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY_PERSONNE = "DELETE FROM PERSONNE WHERE ID = ?";
    private static final String UPDATE_QUERY_PERSONNE = "UPDATE PERSONNE SET NAME = ?, AGE = ? WHERE ID = ?";
    //--------------------------------------------------------------------------------------
    private static final String SELECT_ALL_QUERY_ARTICLE = "SELECT * FROM ARTICLE";
    private static final String SELECT_BY_ID_QUERY_ARTICLE = "SELECT * FROM ARTICLE WHERE ID = ?";
    private static final String INSERT_QUERY_ARTICLE = "INSERT INTO ARTICLE (ID, NAME, AGE) VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID_QUERY_ARTICLE = "DELETE FROM ARTICLE WHERE ID = ?";
    private static final String UPDATE_QUERY_ARTICLE = "UPDATE ARTICLE SET NAME = ?, AGE = ? WHERE ID = ?";
    
    
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

    @Override
    public Personne getById(int id, Connection connection) {
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

    @Override
    public void deleteById(int id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_QUERY_PERSONNE)) {
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            handleAffectedRows(affectedRows, "Suppression de la personne", "Aucune personne trouvée avec l'ID " + id);

        } catch (SQLException e) {
            handleSQLException(e, "Erreur lors de la suppression de la personne");
        }
    }

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
}
