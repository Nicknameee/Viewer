package application.data.articles.repository;

import application.data.articles.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a WHERE a.name=:name")
    Article getArticleByName(@Param("name") String name);

    @Query("SELECT a FROM Article a WHERE a.secret=:secret")
    Article getArticleBySecret(@Param("secret") String secret);

    @Modifying
    @Transactional
    @Query("DELETE FROM Article a WHERE a.name=:title")
    void removeArticleByTitle(@Param("title") String title);
}