package application.web.controllers;

import application.data.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/exists")
    public Map<String , ?> checkUserExistingByUniqueCredentials(@RequestParam("mail") String mail ,
                                                                @RequestParam("username") String username)
    {
        Map<String , Object> response = new HashMap<>();
        try {
            if (userService.getUserByMail(mail) == null && userService.getUserByUsername(username) == null) {
                response.put("success"  , true);
                response.put("error"    , null);
                return response;
            }
            response.put("success"  ,   true);
            response.put("error"    ,   "Credentials are reserved");
        }
        catch (RuntimeException e) {
            response.put("success"  , false);
            response.put("error"    , e.getMessage());
        }
        return response;
    }
}