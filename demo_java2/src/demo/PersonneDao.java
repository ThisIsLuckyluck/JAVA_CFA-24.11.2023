package demo;

import java.sql.Connection;
import java.util.List;

public interface PersonneDao {
    List<Personne> getAllPersons(Connection connection);

    Personne getPersonneById(int id, Connection connection);

    void createPersonne(Personne personne, Connection connection);

    void deletePersonneById(int id, Connection connection);

    void updatePersonne(Personne personne, Connection connection);
}