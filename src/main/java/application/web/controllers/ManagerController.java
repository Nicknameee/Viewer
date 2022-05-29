package application.web.controllers;

import application.api.service.GDriveAPIService;
import application.data.articles.Article;
import application.data.articles.service.ArticleService;
import application.data.loadableResources.service.LoadableResourceService;
import application.data.payment.PaymentModel;
import application.data.payment.models.Bank;
import application.data.payment.service.PaymentService;
import application.data.promo.Promo;
import application.data.promo.models.PromoType;
import application.data.promo.service.PromoService;
import application.data.users.attributes.Role;
import application.data.users.attributes.Status;
import application.data.users.service.UserService;
import application.web.responses.ApplicationWebResponse;
import application.web.responses.manager.*;
import application.web.responses.websocket.ChangesAlertAdminResponse;
import application.web.responses.websocket.ChangesAlertArticleResponse;
import application.web.responses.websocket.ChangesAlertResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/api/manager")
public class ManagerController {
    private UserService userService;

    private PromoService promoService;

    private ArticleService articleService;

    private LoadableResourceService loadableResourceService;

    private PaymentService paymentService;

    private GDriveAPIService driveAPIService;

    @Autowired
    public void setDriveAPIService(GDriveAPIService driveAPIService) {
        this.driveAPIService = driveAPIService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

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
    public ApplicationWebResponse checkUserExistingByUniqueCredentials(@RequestParam("mail")                                String mail ,
                                                                       @RequestParam(value = "username" , required = false) String username)
    {
        CredentialsUniqueCheckingResponse response = new CredentialsUniqueCheckingResponse();
        try {
            if (username != null) {
                if (userService.getUserByMail(mail) == null && userService.getUserByUsername(username) == null) {
                    response.setSuccess(true);
                    response.setError(null);
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
            }
            else {
                if (userService.getUserByMail(mail) == null) {
                    response.setSuccess(true);
                    response.setError(null);
                }
                else {
                    response.setSuccess(false);
                    response.setError("mail");
                }
            }
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PostMapping("/promo/create")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:create')")
    public ApplicationWebResponse createPromo(@RequestParam("type") PromoType type) {
        PromoResponse response = new PromoResponse();
        try {
            promoService.generateNewPromo(type);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
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
            Promo promo = promoService.getPromoByCode(code);
            if (promo != null) {
                promoService.usePromo(code , SecurityContextHolder.getContext().getAuthentication().getName());
                response.setSuccess(true);
                response.setError(null);
            }
            else {
                response.setSuccess(false);
                response.setError("No such promo were found");
            }
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/promo/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:delete')")
    public ApplicationWebResponse deletePromo(@RequestParam("id") Long promoId) {
        PromoResponse response = new PromoResponse();
        try {
            promoService.deletePromo(promoId);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PostMapping("/article/create")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:create' , 'access:moderator:create')")
    public ApplicationWebResponse createArticle(@RequestParam("title")                              String title,
                                                @RequestParam("content")                            String content,
                                                @RequestParam(value = "media" , required = false)   MultipartFile[] files,
                                                @RequestParam(value = "tags" , required = false)    String[] tags) throws Exception {
        ArticleResponse response = new ArticleResponse();
        try {
            Article article = articleService.presetArticle(title , content , files , tags , driveAPIService , loadableResourceService);
            articleService.saveArticle(article);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PutMapping("/article/update")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:update' , 'access:moderator:update')")
    public ApplicationWebResponse updateArticle(@RequestParam("title")                              String title,
                                                @RequestParam("content")                            String content,
                                                @RequestParam(value = "media" , required = false)   MultipartFile[] files,
                                                @RequestParam(value = "tags" , required = false)    String[] tags,
                                                @RequestParam("id")                                 Long id) {
        ArticleResponse response = new ArticleResponse();
        Article article = articleService.getArticleById(id);
        try {
            articleService.updateArticle(article , title , content , files , tags , loadableResourceService , driveAPIService);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/article/delete")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:delete' , 'access:moderator:delete')")
    public ApplicationWebResponse deleteArticle(@RequestParam("id") Long articleId) {
        ArticleResponse response = new ArticleResponse();
        try {
            articleService.removeArticleById(articleId , driveAPIService);
            response.setSuccess(true);
            response.setError(null);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/article/resource")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:delete' , 'access:moderator:delete')")
    public ApplicationWebResponse deleteResourceFromArticle(@RequestParam("filename") String filename,
                                                            @RequestParam("id")       String articleId) {
        ArticleResourceResponse response = new ArticleResourceResponse();
        try {
            Article article = articleService.getArticleById(Long.valueOf(articleId));
            response.setSuccess(false);
            response.setError(null);
            if (article != null) {
                loadableResourceService.deleteLoadableResourceByName(filename ,
                        article.getResources().stream()
                                .filter(file -> file.getFilename().equals(filename)).findFirst().get().getFileId() ,
                        driveAPIService);
                response.setSuccess(true);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PutMapping("/user/update")
    @ResponseBody
    @PreAuthorize("hasAuthority('access:admin:update')")
    public ApplicationWebResponse updateUserRoleAndStatus(@RequestParam("mail")      String mail ,
                                                          @RequestParam("role")      Role role   ,
                                                          @RequestParam("status")    Status status) {
        UserChangesResponse response = new UserChangesResponse();
        try {
            userService.updateUserRoleAndStatus(mail , role , status);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PostMapping("/payment/create")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:create' , 'access:moderator:create')")
    public ApplicationWebResponse addPaymentModel(@RequestParam("card")                            String card,
                                                  @RequestParam(value = "iban" , required = false) String IBAN,
                                                  @RequestParam("bank")                            Bank bank,
                                                  @RequestParam("receiver")                        String receiver) {
        PaymentResponse response = new PaymentResponse();
        try {
            paymentService.savePaymentModel(new PaymentModel(0L , card , IBAN , bank , receiver));
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PutMapping("/payment/update")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:update' , 'access:moderator:update')")
    public ApplicationWebResponse updatePaymentModel(@RequestParam("card")                            String card,
                                                     @RequestParam(value = "iban" , required = false) String IBAN,
                                                     @RequestParam("receiver")                        String receiver,
                                                     @RequestParam("id")                              Long id) {
        PaymentResponse response = new PaymentResponse();
        try {
            paymentService.updatePaymentModel(card , IBAN , receiver , id);
            paymentService.getPaymentModelById(id);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/payment/delete")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:admin:delete' , 'access:moderator:delete')")
    public ApplicationWebResponse deletePaymentModel(@RequestParam("id") Long id) {
        PaymentResponse response = new PaymentResponse();
        try {
            paymentService.deletePaymentModelById(id);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/session/valid")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('access:user:read' , 'access:moderator:read' , 'access:admin:read')")
    public ApplicationWebResponse isSessionValid() {
        SessionValidResponse response = new SessionValidResponse();
        try {
            String mail = SecurityContextHolder.getContext().getAuthentication().getName();
            response.setSuccess(true);
            response.setValid(userService.checkSession(mail));
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setValid(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @MessageMapping("/home/alert")
    @SendTo("/topic/home")
    public ApplicationWebResponse sendAlertToHomePage() {
        ChangesAlertResponse response = new ChangesAlertResponse();
        response.setIsAlert(true);
        response.setSuccess(true);
        response.setError(null);
        return response;
    }

    @MessageMapping("/article/alert")
    @SendTo("/topic/article")
    public ApplicationWebResponse sendAlertToArticlePage(String secret) {
        ChangesAlertArticleResponse response = new ChangesAlertArticleResponse();
        response.setSecret(secret.replaceAll("\"" , ""));
        response.setSuccess(true);
        response.setError(null);
        return response;
    }

    @MessageMapping("/admin/alert")
    @SendTo("/topic/admin")
    public ApplicationWebResponse sendAlertToAdminPage(String meta) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String , String> metadata = mapper.readValue(meta , Map.class);
        ChangesAlertAdminResponse response = new ChangesAlertAdminResponse();
        response.setMail(metadata.get("user"));
        response.setAction(metadata.get("action"));
        response.setSuccess(true);
        response.setError(null);
        return response;
    }
}