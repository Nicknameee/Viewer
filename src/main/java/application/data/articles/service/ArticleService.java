package application.data.articles.service;

import application.api.service.GDriveAPIService;
import application.data.articles.Article;
import application.data.articles.repository.ArticleRepositoryImplementation;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.service.LoadableResourceService;
import application.data.utils.converters.CustomPropertySourceConverter;
import application.data.utils.generators.CodeGenerator;
import application.data.utils.loaders.CustomPropertyDataLoader;
import application.data.utils.threads.TaskDistributorTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
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
        article.getResources().addAll(loadableResourceService.processResourcesForArticle(files , article , driveAPIService));
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
            Runnable dropResource = () -> {
                try {
                    driveAPIService.deleteFile(folderId);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            };
            TaskDistributorTool.execute(dropResource);
        }
    }

    public Article presetArticle(String title, String content , MultipartFile[] files ,
                                 GDriveAPIService driveAPIService , LoadableResourceService loadableResourceService) throws Exception {
        Map<String , String> driveAPIProperties =
                CustomPropertySourceConverter.convertToKeyValueFormat
                        (CustomPropertyDataLoader.getResourceContent("classpath:gdrive/drive.properties"));
        Article article = new Article(0L , title , content , null , null , null , null);
        String folderName = CodeGenerator.generateUniqueCode().toString();
        String folderId = driveAPIService.createDirectory(driveAPIProperties.get("root") + folderName);
        article.setFolderName(folderName);
        article.setFolderId(folderId);
        article.setResources(loadableResourceService.processResourcesForArticle(files , article , driveAPIService));
        return article;
    }
}