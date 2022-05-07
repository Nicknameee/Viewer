package application.data.articles.service;

import application.api.gdrive.service.GDriveAPIService;
import application.data.articles.Article;
import application.data.articles.repository.ArticleRepositoryImplementation;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.service.LoadableResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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

    public Article getArticleById(Long id) {
        return articleRepository.getArticleById(id);
    }

    public Article getArticleBySecret(String secret) {
        return articleRepository.getArticleBySecret(secret);
    }

    public Article saveArticle(Article article) {
        if (article.getSecret() == null) {
            article.setSecret(UUID.randomUUID().toString());
        }
        return articleRepository.saveArticle(article);
    }

    public Article updateArticle(Article article , String title , String content , MultipartFile[] files ,
                                 LoadableResourceService loadableResourceService , GDriveAPIService driveAPIService) {
        List<LoadableResource> addedResources = loadableResourceService.processResourcesForArticle(files , article , driveAPIService);
        article.getResources().addAll(addedResources);
        article.setName(title);
        article.setText(content);
        return saveArticle(article);
    }

    public void removeArticleById(Long id , GDriveAPIService driveAPIService) throws Exception {
        Article article = getArticleById(id);
        if (article != null) {
            removeArticleResources(article.getResources() , article.getFolderId() , driveAPIService);
            article.getResources().clear();
        }
        articleRepository.removeArticleById(id);
    }

    public void removeArticleResources(List<LoadableResource> resources , String folderId , GDriveAPIService driveAPIService) throws Exception {
        if (resources != null && resources.size() > 0) {
            driveAPIService.deleteFile(folderId);
        }
    }
}