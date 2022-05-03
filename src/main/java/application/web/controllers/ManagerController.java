package application.web.controllers;

import application.data.articles.Article;
import application.data.articles.service.ArticleService;
import application.data.loadableResources.LoadableResource;
import application.data.loadableResources.models.ResourceType;
import application.data.loadableResources.service.LoadableResourceService;
import application.data.loadableResources.utils.FileProcessingUtility;
import application.data.promo.Promo;
import application.data.promo.models.PromoType;
import application.data.promo.service.PromoService;
import application.data.users.service.UserService;
import application.web.responses.ApplicationWebResponse;
import application.web.responses.manager.ArticleResponse;
import application.web.responses.manager.CredentialsUniqueCheckingResponse;
import application.web.responses.manager.PromoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/api/manager")
public class ManagerController {
    private UserService userService;

    private PromoService promoService;

    private ArticleService articleService;

    private LoadableResourceService loadableResourceService;

    @Autowired
    public void setLoadableResourceService(LoadableResourceService loadableResourceService) {
        this.loadableResourceService = loadableResourceService;
    }

    @Autowired
    public void setPromoService(PromoService promoService) {
        this.promoService = promoService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/credentials/reserved")
    @ResponseBody
    public ApplicationWebResponse checkUserExistingByUniqueCredentials(@RequestParam("mail") String mail ,
                                                                       @RequestParam(value = "username" , required = false) String username)
    {
        CredentialsUniqueCheckingResponse response = new CredentialsUniqueCheckingResponse();
        try {
            if (username != null) {
                if (userService.getUserByMail(mail) == null && userService.getUserByUsername(username) == null) {
                    response.setSuccess(true);
                    response.setError(null);
                    response.setIsUnique(true);
                    return response;
                }
                response.setSuccess(false);
                if (userService.getUserByMail(mail) != null) {
                    response.setError("mail");
                }
                if (userService.getUserByUsername(username) != null) {
                    response.setError("username");
                }
                if (userService.getUserByMail(mail) != null && userService.getUserByUsername(username) != null) {
                    response.setError("all");
                }
                response.setIsUnique(false);
            }
            else {
                if (userService.getUserByMail(mail) == null) {
                    response.setSuccess(true);
                    response.setError(null);
                    response.setIsUnique(true);
                }
                else {
                    response.setSuccess(false);
                    response.setError("mail");
                    response.setIsUnique(false);
                }
            }
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
            response.setIsUnique(null);
        }
        return response;
    }

    @PostMapping("/promo/create")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:create')")
    public ApplicationWebResponse createPromo(@RequestParam("type") PromoType type) {
        PromoResponse response = new PromoResponse();
        try {
            response.setPromo(promoService.generateNewPromo(type));
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setPromo(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PostMapping("/promo/use")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:user:read' , 'access:admin:read')")
    public ApplicationWebResponse usePromo(@RequestParam("code") String code) {
        PromoResponse response = new PromoResponse();
        try {
            String mail = SecurityContextHolder.getContext().getAuthentication().getName();
            if (userService.getUserByMail(mail) != null) {
                Promo promo = promoService.getPromoByCode(code);
                if (promo != null) {
                    promoService.usePromo(code , mail);
                    response.setPromo(promo);
                    response.setSuccess(true);
                    response.setError(null);
                }
                else {
                    response.setPromo(null);
                    response.setSuccess(false);
                    response.setError("No such promo were found");
                }
            }
            else {
                response.setPromo(null);
                response.setSuccess(false);
                response.setError("No registered users were found by these credentials");
            }
        }
        catch (Exception e) {
            response.setPromo(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }


    @GetMapping("article/read")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:user:read' , 'access:admin:read')")
    public ApplicationWebResponse readArticle(@RequestParam("title") String title) {
        ArticleResponse response = new ArticleResponse();
        try {
            response.setArticle(articleService.getArticleByName(title));
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setArticle(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PostMapping("/article/create")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:create')")
    public ApplicationWebResponse createArticle(@RequestParam("title")   String title,
                                                @RequestParam("content") String content,
                                                @RequestParam(value = "media" , required = false)   MultipartFile[] files) {
        ArticleResponse response = new ArticleResponse();
        Article article = new Article(0L , title , content , null , null);
        try {
            article.setResources(loadableResourceService.processResourcesForArticle(files , article));
            response.setArticle(articleService.saveArticle(article));
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setArticle(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PutMapping("/article/update")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:update')")
    public ApplicationWebResponse updateArticle(@RequestParam("title")                              String title,
                                                @RequestParam("content")                            String content,
                                                @RequestParam(value = "media" , required = false)   MultipartFile[] files,
                                                @RequestParam("id")                                 Long id) {
        ArticleResponse response = new ArticleResponse();
        Article article = articleService.getArticleById(id);
        try {
            List<LoadableResource> addedResources = loadableResourceService.processResourcesForArticle(files , article);
            article.getResources().addAll(addedResources);
            article.setName(title);
            article.setText(content);
            response.setArticle(articleService.saveArticle(article));
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setArticle(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/article/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:delete')")
    public ApplicationWebResponse deleteArticle(@RequestParam("id") Long id) {
        ArticleResponse response = new ArticleResponse();
        try {
            articleService.removeArticleById(id);
            response.setArticle(null);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setArticle(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/article/resource")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:delete')")
    public ApplicationWebResponse deleteResourceFromArticle(@RequestParam("filename") String filename,
                                                            @RequestParam("id")       String articleId) {
        ArticleResponse response = new ArticleResponse();
        try {
            Article article = articleService.getArticleById(Long.valueOf(articleId));
            response.setSuccess(false);
            response.setArticle(null);
            response.setError(null);
            if (article != null) {
                loadableResourceService.deleteLoadableResourceByName(filename);
                response.setSuccess(true);
            }
        }
        catch (RuntimeException e) {
            response.setArticle(null);
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/resource/{filename}")
    @PreAuthorize("hasAuthority('access:admin:delete')")
    public String getResource(@PathVariable("filename") String filename , Model model) {
        LoadableResource loadableResource = loadableResourceService.getLoadableResourceByFilename(filename);
        model.addAttribute("file" , loadableResource);
        return "/all/resource";
    }
}