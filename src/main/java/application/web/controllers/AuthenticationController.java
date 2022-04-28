package application.web.controllers;

import application.data.users.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/authentication")
public class AuthenticationController {
    @GetMapping("/user/login")
    public String login(Model model , @RequestParam(value = "error" , required = false) String error , HttpSession session) {
        if (error != null) {
            session.invalidate();
            model.addAttribute("message" , "Wrong username or password");
        }
        return "/user/authentication/login";
    }


    @GetMapping("/user/register")
    public String register(Model model) {
        model.addAttribute("userModel" , new User());
        return "/user/registration/register";
    }

    @GetMapping("/user/restore/password")
    public String restorePassword() {
        return "/user/restoring/password";
    }
}