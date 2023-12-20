package demo;

import java.sql.Connection;
import java.util.List;

public interface CommandeDao {
    List<Commande> getAllCommandes(Connection connection);

    Commande getCommandeById(int id, Connection connection);

    void createCommande(Commande commande, Connection connection);

    void deleteCommandeById(int id, Connection connection);

    void updateCommande(Commande commande, Connection connection);
}
