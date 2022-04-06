package application.web.controllers;

import application.data.users.User;
import application.data.users.service.UserService;
import application.data.verification.VerificationData;
import application.data.verification.service.VerificationDataService;
import application.web.responses.SimpleHttpResponseTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    private VerificationDataService verificationService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVerificationService(VerificationDataService verificationService) {
        this.verificationService = verificationService;
    }

    @ResponseBody
    @PostMapping("/register")
    public SimpleHttpResponseTemplate register(@ModelAttribute("userModel") User user) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            userService.saveUser(user);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/confirm")
    public SimpleHttpResponseTemplate confirmVerification(@ModelAttribute("verificationData") VerificationData verificationData) {
        try {
            if (!verificationService.checkVerificationDataCoincidence(verificationData)) {
                return new SimpleHttpResponseTemplate(false , "Codes does not match");
            }
            verificationService.deleteVerificationData(verificationData);
            return new SimpleHttpResponseTemplate(true , null);
        }
        catch (RuntimeException e) {
            return new SimpleHttpResponseTemplate(false , e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/update/password")
    public SimpleHttpResponseTemplate updateUser(@RequestParam("mail") String mail ,
                                                 @RequestParam("newPassword") String password) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            User user = userService.getUserByMail(mail);
            user.setPassword(password);
            userService.updateUser(user);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/personal")
    @PreAuthorize("hasAuthority('access:user:read')")
    public String personalPage(Model model) {
        User user = userService.getUserByMail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user" , user);
        return "/user/personal/profile";
    }
}