package application.web.controllers;

import application.data.promo.models.PromoType;
import application.data.promo.service.PromoService;
import application.data.users.User;
import application.data.users.service.UserService;
import application.web.responses.SimpleHttpResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/manager")
public class ManagerController {
    private UserService userService;

    private PromoService promoService;

    @Autowired
    public void setPromoService(PromoService promoService) {
        this.promoService = promoService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/credentials/reserved")
    @ResponseBody
    public SimpleHttpResponseTemplate checkUserExistingByUniqueCredentials(@RequestParam("mail") String mail ,
                                                                           @RequestParam(value = "username" , required = false) String username)
    {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            if (username != null) {
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
            else {
                if (userService.getUserByMail(mail) == null) {
                    response.setSuccess(true);
                    response.setError(null);
                    return response;
                }
                response.setSuccess(false);
                response.setError("mail");
            }
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/personal")
    @PreAuthorize("hasAuthority('access:admin:read')")
    public String personalPage(Model model) {
        User user = userService.getUserByMail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user" , user);
        return "/user/personal/profile";
    }

    @PostMapping("/promo/create")
    @ResponseBody
    public SimpleHttpResponseTemplate createPromo(@RequestParam("type") PromoType type) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            promoService.generateNewPromo(type);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @PostMapping("/promo/use")
    @ResponseBody
    public SimpleHttpResponseTemplate usePromo(@RequestParam("code") String code ,
                                               @RequestParam("mail") String mail) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        try {
            promoService.usePromo(code , mail);
            response.setSuccess(true);
            response.setError(null);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }
}