package application.data.articles.repository;

import application.data.articles.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a ORDER BY a.id DESC")
    List<Article> getAllArticles();
    @Query("SELECT a FROM Article  a WHERE a.id=:id")
    Article getArticleById(@Param("id") Long id);

    @Query("SELECT a FROM Article a WHERE a.secret=:secret")
    Article getArticleBySecret(@Param("secret") String secret);

    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a.id=:id")
    void deleteArticleById(@Param("id") Long id);
}