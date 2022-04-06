package application.data.mail.utils;

import application.data.mail.models.MailMessageDataCollector;
import application.data.users.models.UserActionType;
import application.data.utils.converters.CustomPropertySourceConverter;
import application.data.utils.loaders.CustomPropertyDataLoader;

import java.util.Map;

public class MessageRenderService {
    public static MailMessageDataCollector renderCollectorData( MailMessageDataCollector collector  ,
                                                                UserActionType userActionType       ,
                                                                String verificationCode             ) {
        Map<String , String> nameProperties =
                CustomPropertySourceConverter.convertToKeyValueFormat
                        (CustomPropertyDataLoader.getResourceContent("classpath:naming/names.ps"));
        String subject;
        String htmlTemplate;
        switch (userActionType) {
            case RESTORE_PASSWORD:
                subject = CustomPropertyDataLoader
                        .getResourceContent("classpath:mail/events/RESTORE_PASSWORD/titles/restore_password.txt")
                        .replace("{name}" , nameProperties.get("web.name"));
                htmlTemplate =
                        CustomPropertyDataLoader
                                .getResourceContent("classpath:mail/events/RESTORE_PASSWORD/templates/restore_password_v2.html")
                                .replace("{name}" , nameProperties.get("web.name"))
                                .replace("{code}" , verificationCode);

                break;
            case CONFIRM_REGISTRATION:
                subject = CustomPropertyDataLoader
                        .getResourceContent("classpath:mail/events/CONFIRM_REGISTRATION/titles/confirm_registration.txt")
                        .replace("{name}" , nameProperties.get("web.name"));
                htmlTemplate =
                        CustomPropertyDataLoader
                                .getResourceContent("classpath:mail/events/CONFIRM_REGISTRATION/templates/confirm_registration_v2.html")
                                .replace("{name}" , nameProperties.get("web.name"))
                                .replace("{code}" , verificationCode);
                break;
            case CONFIRM_AUTHORIZATION:
                subject = CustomPropertyDataLoader
                        .getResourceContent("classpath:mail/events/CONFIRM_AUTHORIZATION/titles/confirm_authorization.txt")
                        .replace("{name}" , nameProperties.get("web.name"));
                htmlTemplate =
                        CustomPropertyDataLoader
                                .getResourceContent("classpath:mail/events/CONFIRM_AUTHORIZATION/templates/confirm_authorization_v2.html")
                                .replace("{name}" , nameProperties.get("web.name"))
                                .replace("{code}" , verificationCode);
                break;
            default:
                subject = "Error: unknown action , can not find template";
                htmlTemplate = "<h1>Error occurs while generating message , please , try again</h1>";
                break;
        }
        collector.setSubject(subject);
        collector.setText(htmlTemplate);
        return new MailMessageDataCollector(collector);
    }
}