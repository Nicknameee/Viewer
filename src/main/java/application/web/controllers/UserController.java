package application.web.controllers;

import application.data.users.User;
import application.data.users.repository.UserRepositoryImplementation;
import application.data.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String , ?> register(@ModelAttribute("userModel") User user) {
        logger.info("User: " + user);
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
}