package application.web.controllers;

import application.data.users.service.UserService;
import application.web.responses.SimpleHttpResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/exists")
    public SimpleHttpResponseTemplate checkUserExistingByUniqueCredentials(@RequestParam("mail") String mail ,
                                                                           @RequestParam("username") String username)
    {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            if (userService.getUserByMail(mail) == null && userService.getUserByUsername(username) == null) {
                response.setSuccess(true);
                response.setError(null);
                return response;
            }
            response.setSuccess(false);
            if (userService.getUserByMail(mail) != null) {
                response.setError("mail");
            }
            if (userService.getUserByUsername(username) != null) {
                response.setError("username");
            }
            if (userService.getUserByMail(mail) != null && userService.getUserByUsername(username) != null) {
                response.setError("all");
            }
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }
}