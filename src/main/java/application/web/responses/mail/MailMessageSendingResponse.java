package application.web.responses.mail;

import application.data.mail.models.MailMessageDataCollector;
import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MailMessageSendingResponse extends ApplicationWebResponse {
    private MailMessageDataCollector collector;
}