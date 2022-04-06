package application.data.mail.service;

import application.data.mail.models.MailMessageDataCollector;
import application.data.mail.models.MailType;
import application.data.users.models.UserActionType;
import application.data.utils.converters.CustomPropertySourceConverter;
import application.data.utils.generators.CodeGenerator;
import application.data.utils.loaders.CustomPropertyDataLoader;
import application.data.verification.VerificationData;
import application.data.verification.service.VerificationDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MailService {
    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    private VerificationDataService verificationDataService;

    private JavaMailSender mailSenderTool;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Autowired
    public void setMailSender(JavaMailSender mailSender)
    {
        this.mailSenderTool = mailSender;
    }

    @Autowired
    public void setVerificationDataService(VerificationDataService verificationDataService) {
        this.verificationDataService = verificationDataService;
    }

    public synchronized Boolean sendMessage(MailMessageDataCollector collector   ,
                                            MailType mailType)
            throws MessagingException {
        switch (mailType) {
            case PLAIN_TEXT:
                return sendPlainTextMessage(collector);
            case HTML:
                return sendHtmlMessage(collector);
            case MEDIA:
                return sendMediaMessage(collector);
        }
        return false;
    }

    private Boolean sendPlainTextMessage(MailMessageDataCollector collector) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUsername);
            message.setTo(collector.getRecipient());
            message.setSubject(collector.getSubject());
            message.setText(collector.getText());
            message.setSentDate(new Date());
            mailSenderTool.send(message);
        }
        catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    private Boolean sendMediaMessage(MailMessageDataCollector collector)
            throws MessagingException {
        try {
            MimeMessage message = mailSenderTool.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(mailUsername);
            helper.setTo(collector.getRecipient());
            helper.setSubject(collector.getSubject());
            helper.setText(collector.getText());
            helper.setSentDate(new Date());
            collector.getResources()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(resource -> {
                        try {
                            helper.addAttachment("Resource" , resource);
                        }
                        catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    });
            mailSenderTool.send(message);
        }
        catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    private Boolean sendHtmlMessage(MailMessageDataCollector collector) {
        try {
            MimeMessage mimeMessage = mailSenderTool.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(mailUsername);
            helper.setTo(collector.getRecipient());
            helper.setSubject(collector.getSubject());
            helper.setText(collector.getText() , true);
            mailSenderTool.send(mimeMessage);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public synchronized MailMessageDataCollector generateVerificationMailMessage
            (MailMessageDataCollector collector ,
             UserActionType userActionType)
            throws IOException {
        VerificationData verificationDataRow =
                verificationDataService.getVerificationDataByMail(collector.getRecipient());
        String verificationCode = CodeGenerator
                .generateRandomVerificationUUIDCode().toString();
        if (verificationDataRow != null) {
            verificationDataRow.setActionType(userActionType);
            verificationDataRow.setCode(verificationCode);
            verificationDataService
                    .updateVerificationData(verificationDataRow);
        }
        else {
            verificationDataService
                    .saveVerificationData(
                            new VerificationData(0L, collector.getRecipient(), userActionType, verificationCode)
                    );
        }
        Map<String , String> nameProperties =
                CustomPropertySourceConverter.convertToKeyValueFormat
                        (CustomPropertyDataLoader.getResourceContent("classpath:naming/names.ps"));
        switch (userActionType) {
            case RESTORE_PASSWORD:
                break;
            case CONFIRM_REGISTRATION:
                String subject = CustomPropertyDataLoader
                        .getResourceContent("classpath:mail/titles/register_confirmation.txt")
                        .replace("{name}" , nameProperties.get("web.name"));
                String htmlTemplate =
                        CustomPropertyDataLoader
                        .getResourceContent("classpath:mail/templates/register_confirmation_with_bg.html")
                        .replace("{name}" , nameProperties.get("web.name"))
                        .replace("{code}" , verificationCode);
                collector.setSubject(subject);
                collector.setText(htmlTemplate);
                break;
            case CONFIRM_AUTHORIZATION:
                break;
        }
        return new MailMessageDataCollector(collector);
    }
}