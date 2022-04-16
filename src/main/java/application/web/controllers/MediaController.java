package application.web.controllers;

import application.data.articles.Article;
import application.data.articles.service.ArticleService;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.models.ResourceType;
import application.data.loadableResources.service.LoadableResourceService;
import application.data.loadableResources.utils.FileProcessingUtility;
import application.web.responses.SimpleHttpResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/api/media")
public class MediaController {
    private LoadableResourceService loadableResourceService;

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setLoadableResourceService(LoadableResourceService loadableResourceService) {
        this.loadableResourceService = loadableResourceService;
    }

    @PostMapping("/upload")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:create')")
    public SimpleHttpResponseTemplate uploadMediaForArticle(@RequestParam("media") MultipartFile[] files ,
                                                            @ModelAttribute("article") Article article) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            List<LoadableResource> resourceList = new LinkedList<>();
            for (MultipartFile file : files) {
                String name = FileProcessingUtility.uploadFile(file);
                ResourceType type = file.getContentType().contains("image")
                        ? ResourceType.IMAGE : ResourceType.VIDEO;
                resourceList.add(new LoadableResource(name , type , file.getSize() , article));
            }
            article.setResources(resourceList);
            articleService.saveArticle(article);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }
}