package application.web.controllers;

import application.data.articles.Article;
import application.data.articles.service.ArticleService;
import application.data.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/all")
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
    public String home(Model model) {
        model.addAttribute("payments" , paymentService.getAll());
        model.addAttribute("articles" , articleService.getAll());
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        return "/all/home";
    }

    @GetMapping("/article/{secret}")
    public Object article(@PathVariable("secret") String secret , Model model) {
        Article article = articleService.getArticleBySecret(secret);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        model.addAttribute("article" , article);
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        return "/all/article";
    }
}