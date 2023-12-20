package demo;

import java.sql.Connection;
import java.util.List;

public interface ArticleDao {
    List<Article> getAllArticles(Connection connection);

    Article getArticleById(int id, Connection connection);

    void createArticle(Article article, Connection connection);

    void deleteArticleById(int id, Connection connection);

    void updateArticle(Article article, Connection connection);
}