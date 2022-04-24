package application.data.articles.service;

import application.data.articles.Article;
import application.data.articles.repository.ArticleRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepositoryImplementation articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepositoryImplementation articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAll() {
        return articleRepository.getAll();
    }
    public Article getArticleByName(String name) {
        return articleRepository.getArticleByName(name);
    }

    public Article saveArticle(Article article) {
        return articleRepository.saveArticle(article);
    }

    public void removeArticleByTitle(String title) {
        Article article = getArticleByName(title);
        if (article != null) {
            article.getResources().clear();
        }
        articleRepository.removeArticleByTitle(title);
    }
}