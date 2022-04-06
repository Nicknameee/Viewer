package application.web.controllers;

import application.data.mail.models.MailMessageDataCollector;
import application.data.mail.models.MailType;
import application.data.mail.service.MailService;
import application.data.users.models.UserActionType;
import application.data.utils.threads.TaskDistributorTool;
import application.web.responses.SimpleHttpResponseTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

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
    public SimpleHttpResponseTemplate verify
            (@RequestParam("recipient")                                        String recipient                    ,
             @RequestParam(value = "subject" , required = false)               String subject                      ,
             @RequestParam(value = "text" , required = false)                  String text                         ,
             @RequestParam(value = "resources" , required = false)             List<Resource> resources            ,
             @RequestParam("mailType")                                         MailType mailType                   ,
             @RequestParam("userActionType")                                   UserActionType userActionType) {
        SimpleHttpResponseTemplate response = new SimpleHttpResponseTemplate();
        MailMessageDataCollector collector = new MailMessageDataCollector(recipient , subject , text , resources);
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
            response.setSuccess(true);
            response.setError(null);
        }
        catch (RuntimeException e) {
            response.setSuccess(false);
            response.setError(e.getMessage());
        }
        return response;
    }
}