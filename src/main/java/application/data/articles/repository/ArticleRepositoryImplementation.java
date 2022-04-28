package application.data.articles.repository;

import application.data.articles.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryImplementation {
    private ArticleRepository articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public Article getArticleByName(String name) {
        return articleRepository.getArticleByName(name);
    }

    public Article getArticleBySecret(String secret) {
        return articleRepository.getArticleBySecret(secret);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public void updateArticle(Article article) {
        articleRepository.updateArticle(article.getName() , article.getText() , article.getId());
    }

    public void removeArticleByTitle(String title) {
        articleRepository.removeArticleByTitle(title);
    }
}