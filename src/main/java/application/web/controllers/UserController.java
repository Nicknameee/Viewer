package application.web.controllers;

import application.data.articles.service.ArticleService;
import application.data.payment.models.Bank;
import application.data.payment.service.PaymentService;
import application.data.promo.models.PromoType;
import application.data.promo.service.PromoService;
import application.data.users.User;
import application.data.users.attributes.Role;
import application.data.users.service.UserService;
import application.data.verification.VerificationData;
import application.data.verification.service.VerificationDataService;
import application.web.responses.ApplicationWebResponse;
import application.web.responses.user.UserResponse;
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

    private ArticleService articleService;

    private PaymentService paymentService;

    private PromoService promoService;

    @Autowired
    public void setPromoService(PromoService promoService) {
        this.promoService = promoService;
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

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
    public ApplicationWebResponse register(@ModelAttribute("userModel") User user , @RequestParam("code") String code) {
        UserResponse response = new UserResponse();
        try {
            VerificationData verificationData = verificationService.getVerificationDataByMail(user.getMail());
            if (verificationData == null) {
                response.setSuccess(false);
                response.setError("Confirmation error , please try again");
                return response;
            }
            if (!verificationData.getCode().equals(code)) {
                response.setSuccess(false);
                response.setError("Confirmation error , please try again");
                return response;
            }
            verificationService.deleteVerificationDataByCode(code);
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
    public ApplicationWebResponse confirmVerification(@ModelAttribute("verificationData") VerificationData verificationData) {
        try {
            if (!verificationService.checkVerificationDataCoincidence(verificationData)) {
                return new UserResponse(false , "Codes does not match");
            }
            return new UserResponse(true , null);
        }
        catch (RuntimeException e) {
            return new UserResponse(false , e.getMessage());
        }
    }

    @ResponseBody
    @PutMapping("/update/password")
    public ApplicationWebResponse updatePassword(@RequestParam("mail")        String mail     ,
                                                 @RequestParam("newPassword") String password ,
                                                 @RequestParam("code")        String code) {
        UserResponse response = new UserResponse();
        try {
            VerificationData verificationData = verificationService
                    .getVerificationDataByMail(mail);
            if (verificationData == null) {
                response.setSuccess(false);
                response.setError("Confirmation error , please try again");
                return response;
            }
            if (!verificationData.getCode().equals(code)) {
                response.setSuccess(false);
                response.setError("Confirmation error , please try again");
                return response;
            }
            verificationService.deleteVerificationDataByCode(code);
            User user = userService.getUserByMail(mail);
            if (user != null) {
                user.setPassword(password);
                userService.updatePassword(user);
                response.setSuccess(true);
                response.setError(null);
                return response;
            }
            response.setSuccess(false);
            response.setError("No registered users were found by these credentials");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/personal")
    @PreAuthorize("hasAnyAuthority('access:user:read' , 'access:moderator:read' , 'access:admin:read')")
    public String personalPage(Model model) {
        User user = userService.getUserByMail(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user" , user);
        switch (user.getRole()) {
            case ROLE_USER:
                return "/user/personal/user";
            case ROLE_MODERATOR:
            case ROLE_ADMIN:
                model.addAttribute("users" , userService.getAllUsersExceptCurrent());
                model.addAttribute("articles" , articleService.getAll());
                model.addAttribute("banks" , Bank.values());
                model.addAttribute("payments" , paymentService.getAll());
                model.addAttribute("promoList" , promoService.findAll());
                model.addAttribute("promoTypes" , PromoType.values());
                model.addAttribute("roles" , Role.values());
                return "/manager/personal/admin";
        }
        return "/user/authentication/login";
    }
}