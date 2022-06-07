package application.data.articles.service;

import application.api.service.GDriveAPIService;
import application.data.articles.Article;
import application.data.articles.attributes.Tag;
import application.data.articles.repository.ArticleRepositoryImplementation;
import application.data.articles.repository.tags.TagRepositoryImplementation;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.service.LoadableResourceService;
import application.data.utils.converters.CustomPropertySourceConverter;
import application.data.utils.generators.CodeGenerator;
import application.data.utils.loaders.CustomPropertyDataLoader;
import application.data.utils.threads.TaskDistributorTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ArticleService {
    private ArticleRepositoryImplementation articleRepository;

    private TagRepositoryImplementation tagRepository;

    @Autowired
    public void setTagRepository(TagRepositoryImplementation tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setArticleRepository(ArticleRepositoryImplementation articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<String> getDistinctTags() {
        return tagRepository.getDistinctTags();
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

    public Article updateArticle(Article article , String title , String content , MultipartFile[] files , String[] tags ,
                                 LoadableResourceService loadableResourceService , GDriveAPIService driveAPIService) {
        if (article.getTags() != null && article.getTags().size() > 0) {
            article.getTags().clear();
            tagRepository.deleteTags(article);
        }
        if (tags != null && tags.length > 0) {
            article.setTags(processTagsForArticle(tags , article));
        }
        article.getResources().addAll(loadableResourceService.processResourcesForArticle(files , article , driveAPIService));
        article.setName(title);
        article.setText(content);
        return saveArticle(article);
    }

    public void removeArticleById(Long id , GDriveAPIService driveAPIService) throws Exception {
        Article article = getArticleById(id);
        if (article != null) {
            tagRepository.deleteTags(article);
            removeArticleResources(article.getResources() , article.getFolderId() , driveAPIService);
            article.getResources().clear();
            article.getTags().clear();
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

    public Article presetArticle(String title, String content , MultipartFile[] files , String[] tags ,
                                 GDriveAPIService driveAPIService , LoadableResourceService loadableResourceService) throws Exception {
        Map<String , String> driveAPIProperties =
                CustomPropertySourceConverter.convertToKeyValueFormat
                        (CustomPropertyDataLoader.getResourceContent("classpath:gdrive/drive.properties"));
        Article article = new Article(0L , title , content , null , null , null , null , null);
        String folderName = CodeGenerator.generateUniqueCode().toString();
        String folderId = driveAPIService.createDirectory(driveAPIProperties.get("root") + folderName);
        article.setFolderName(folderName);
        article.setFolderId(folderId);
        article.setResources(loadableResourceService.processResourcesForArticle(files , article , driveAPIService));
        article.setTags(processTagsForArticle(tags , article));
        return article;
    }

    private List<Tag> processTagsForArticle(String[] tags , Article article) {
        if (tags == null || tags.length == 0) {
            return new ArrayList<>();
        }
        List<Tag> tagList = new ArrayList<>();
        for (String tag : tags) {
            tagList.add(new Tag(0L , tag , article));
        }
        return tagList;
    }

    public void removeTagByName(String name) {
        tagRepository.deleteTagByName(name);
    }
}