package application.web.controllers;

import application.data.users.User;
import application.data.users.repository.UserRepositoryImplementation;
import application.data.users.service.UserService;
import application.data.verification.VerificationData;
import application.data.verification.service.VerificationDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String , ?> register(@ModelAttribute("userModel") User user) {
        Map<String , Object> response = new HashMap<>();
        try {
            userService.saveUser(user);
            response.put("success" , true);
            response.put("error" , null);
        }
        catch (Exception e) {
            response.put("success" , false);
            response.put("error" , e.getMessage());
        }
        return response;
    }

    @PostMapping("/register/confirm")
    public Map<String , ?> confirmRegistering(@ModelAttribute("verificationData") VerificationData verificationData) {
        logger.info(String.valueOf(verificationData));
        Map<String , Object> response = new HashMap<>();
        try {
            if (!verificationService.checkVerificationDataCoincidence(verificationData)) {
                response.put("success"  , false);
                response.put("error"    , "Verification code is incorrect");
                return response;
            }
            verificationService.deleteVerificationData(verificationData);
            response.put("success"  , true);
            response.put("error"    , null);
        }
        catch (RuntimeException e) {
            response.put("success"  , false);
            response.put("error"    , e.getMessage());
        }
        return response;
    }
}