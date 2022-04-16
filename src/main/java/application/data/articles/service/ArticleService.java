package application.data.articles.service;

import application.data.articles.Article;
import application.data.articles.repository.ArticleRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private ArticleRepositoryImplementation articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepositoryImplementation articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article getArticleByName(String name) {
        return articleRepository.getArticleByName(name);
    }

    public void saveArticle(Article article) {
        articleRepository.saveArticle(article);
    }
}