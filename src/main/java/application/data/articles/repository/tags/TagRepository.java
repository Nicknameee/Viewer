package application.data.articles.repository.tags;

import application.data.articles.Article;
import application.data.articles.attributes.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag , Long> {
    @Query("SELECT t.tag FROM Tag t GROUP BY t.tag")
    List<String> getDistinctTags();

    @Modifying
    @Transactional
    @Query("DELETE FROM Tag t WHERE t.article=:article")
    void deleteTags(@Param("article") Article article);
}
