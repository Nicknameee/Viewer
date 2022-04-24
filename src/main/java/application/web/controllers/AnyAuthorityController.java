package application.web.controllers;

import application.data.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/all")
public class AnyAuthorityController {
    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("articles" , articleService.getAll());
        model.addAttribute("authenticated" , !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        return "/all/home";
    }
}