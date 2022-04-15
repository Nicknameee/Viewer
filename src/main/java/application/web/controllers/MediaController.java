package application.web.controllers;

import application.data.loadableResources.service.LoadableResourceService;
import application.web.responses.SimpleHttpResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/media")
public class MediaController {
    private LoadableResourceService loadableResourceService;

    @Autowired
    public void setLoadableResourceService(LoadableResourceService loadableResourceService) {
        this.loadableResourceService = loadableResourceService;
    }

    @PostMapping("/upload")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:create')")
    public SimpleHttpResponseTemplate uploadMediaForArticle(@RequestParam("media") MultipartFile[] files ,
                                                            @RequestParam("articleId") Integer articleId) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            loadableResourceService.processResourcesForArticle(files , articleId);
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