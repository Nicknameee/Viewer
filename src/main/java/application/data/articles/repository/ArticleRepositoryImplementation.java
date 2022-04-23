package application.data.articles.repository;

import application.data.articles.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryImplementation {
    private ArticleRepository articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article getArticleByName(String name) {
        return articleRepository.getArticleByName(name);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }
}