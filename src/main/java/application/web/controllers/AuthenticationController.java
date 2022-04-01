package application.web.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @GetMapping("/login")
    public String login() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        return "/user/authentication/login";
    }

    @GetMapping("/register")
    public String register() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        return "/user/registration/register";
    }
}