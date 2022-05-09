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
    public String login(Model model,
                        HttpSession session,
                        @RequestParam(value = "logout" , required = false)           Boolean logout,
                        @RequestParam(value = "session_invalid" , required = false)  Boolean sessionInvalid,
                        @RequestParam(value = "credentials_good" , required = false) Boolean credentials) {
        if (credentials != null && !credentials) {
            session.invalidate();
            model.addAttribute("message" , "Wrong username or password");
        }
        if (logout != null && logout) {
            session.invalidate();
            model.addAttribute("message" , "You have logout successfully");
        }
        if (sessionInvalid != null && sessionInvalid) {
            session.invalidate();
            model.addAttribute("message" , "Your session expired , login again");
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