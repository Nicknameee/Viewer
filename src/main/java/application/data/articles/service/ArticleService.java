package application.data.articles.service;

import application.data.articles.Article;
import application.data.articles.repository.ArticleRepositoryImplementation;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.utils.FileProcessingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Article getArticleBySecret(String secret) {
        return articleRepository.getArticleBySecret(secret);
    }

    public Article saveArticle(Article article) {
        article.setSecret(UUID.randomUUID().toString());
        return articleRepository.saveArticle(article);
    }

    public void updateArticle(Article article) {
        articleRepository.updateArticle(article);
    }

    public void removeArticleByTitle(String title) {
        Article article = getArticleByName(title);
        if (article != null) {
            removeArticleResources(article.getResources());
            article.getResources().clear();
        }
        articleRepository.removeArticleByTitle(title);
    }

    public Boolean removeArticleResources(List<LoadableResource> resources) {
        if (resources != null && resources.size() > 0) {
            List<String> files = resources.stream().map(LoadableResource::getFilename).collect(Collectors.toList());
            return FileProcessingUtility.deleteFiles(files);
        }
        return true;
    }
}