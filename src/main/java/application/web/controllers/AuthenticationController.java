package application.web.controllers;

import application.data.users.User;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @GetMapping("/user/login")
    public String login() {
        //SecurityContextHolder.getContext().setAuthentication(null);
        return "/user/authentication/login";
    }

    @GetMapping("/user/register")
    public String register(Model model) {
        //SecurityContextHolder.getContext().setAuthentication(null);
        model.addAttribute("userModel" , new User());
        return "/user/registration/register";
    }

    @GetMapping("/user/restore/password")
    public String restorePassword() {
        //SecurityContextHolder.getContext().setAuthentication(null);
        return "/user/restoring/password";
    }
}