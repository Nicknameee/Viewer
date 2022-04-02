package application.web.controllers;

import application.data.mail.models.MailMessageDataCollector;
import application.data.mail.models.MailType;
import application.data.mail.service.MailService;
import application.data.users.models.UserActionType;
import application.data.utils.threads.TaskDistributorTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    Logger logger = LoggerFactory.getLogger(MailController.class);

    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/verification")
    public Map<String , ?> verify
            (@RequestParam("recipient")             String recipient                    ,
             @RequestParam("subject")               String subject                      ,
             @RequestParam("text")                  String text                         ,
             @RequestParam("resources")             List<Resource> resources            ,
             @RequestParam("mailType")              MailType mailType                   ,
             @RequestParam("userActionType")        UserActionType userActionType) {
        MailMessageDataCollector collector = new MailMessageDataCollector(recipient , subject , text , resources);
        Map<String , Object> response = new HashMap<>();
        try {
            Runnable messageSendingTask = () -> {
                try {
                    MailMessageDataCollector confirmationMessage =
                            mailService.generateVerificationMailMessage(collector , userActionType);
                    mailService.sendMessage(confirmationMessage , mailType);
                }
                catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            };
            TaskDistributorTool.execute(messageSendingTask);
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