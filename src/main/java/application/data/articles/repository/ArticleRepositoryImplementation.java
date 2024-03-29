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
        return articleRepository.getAllArticles();
    }

    public Article getArticleById(Long id) {
        return articleRepository.getArticleById(id);
    }

    public Article getArticleBySecret(String secret) {
        return articleRepository.getArticleBySecret(secret);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public void removeArticleById(Long id) {
        articleRepository.deleteArticleById(id);
    }
}