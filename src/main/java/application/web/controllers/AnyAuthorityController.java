package application.web.controllers;

import application.data.articles.Article;
import application.data.articles.service.ArticleService;
import application.data.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/all")
@CrossOrigin(origins = "*")
public class AnyAuthorityController {
    private ArticleService articleService;

    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/home")
    public String home(Model model , @RequestParam(value = "lang" , required = false) String language) {
        model.addAttribute("payments" , paymentService.getAll());
        model.addAttribute("articles" , articleService.getAll());
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        if (language == null || language.isEmpty() || language.equals("EN")) {
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
        Article article = articleService.getArticleBySecret(secret);
        if (article == null) {
            if (language == null || language.isEmpty() || language.equals("EN")) {
                return "redirect:/api/all/home?lang=EN";
            }
            return "redirect:/api/all/home";
        }
        model.addAttribute("article" , article);
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        if (language == null || language.isEmpty() || language.equals("EN")) {
            model.addAttribute("lang" , "EN");
            return "/all/article";
        }
        model.addAttribute("lang" , "UA");
        return "/all/articleUA";
    }
}