package application.web.controllers;

import application.data.users.User;
import application.data.users.service.UserService;
import application.data.verification.VerificationData;
import application.data.verification.service.VerificationDataService;
import application.web.responses.SimpleHttpResponseTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @PostMapping("/confirm/register")
    public SimpleHttpResponseTemplate confirmRegistering(@ModelAttribute("verificationData") VerificationData verificationData) {
        logger.info(String.valueOf(verificationData));
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
}