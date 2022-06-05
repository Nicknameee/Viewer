package application.web.controllers;

import application.data.articles.Article;
import application.data.articles.service.ArticleService;
import application.data.payment.service.PaymentService;
import application.data.users.User;
import application.data.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/all")
public class AnyAuthorityController {
    private ArticleService articleService;

    private PaymentService paymentService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/home")
    public String home(Model model,
                       @RequestParam(value = "lang" , required = false) String language) {
        if (language == null || language.isEmpty()) {
            User user = userService.checkAuthentication();
            if (user != null) {
                language = user.getLanguage().name();
            }
        }
        model.addAttribute("payments" , paymentService.getAll());
        model.addAttribute("articles" , articleService.getAll());
        model.addAttribute("tags" , articleService.getDistinctTags());
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        boolean isEnglish = language == null || language.isEmpty() || language.equals("EN");
        if (isEnglish) {
            model.addAttribute("lang" , "EN");
            return "/all/home";
        }
        model.addAttribute("lang" , "UA");
        return "/all/homeUA";
    }

    @GetMapping("/article/{secret}")
    public String article(@PathVariable("secret")                          String secret,
                          @RequestParam(value = "lang" , required = false) String language,
                          Model model) {
        if (language == null || language.isEmpty()) {
            User user = userService.checkAuthentication();
            if (user != null) {
                language = user.getLanguage().name();
            }
        }
        Article article = articleService.getArticleBySecret(secret);
        boolean isEnglish = language == null || language.isEmpty() || language.equals("EN");
        if (article == null) {
            if (isEnglish) {
                return "redirect:/api/all/home?lang=EN";
            }
            return "redirect:/api/all/home";
        }
        model.addAttribute("article" , article);
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        if (isEnglish) {
            model.addAttribute("lang" , "EN");
            return "/all/article";
        }
        model.addAttribute("lang" , "UA");
        return "/all/articleUA";
    }
}