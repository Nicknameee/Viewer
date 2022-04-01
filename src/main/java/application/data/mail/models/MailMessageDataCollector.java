package application.data.mail.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailMessageDataCollector {
    private String recipient;
    private String subject;
    private String text;
    private List<Resource> resources;

    public MailMessageDataCollector(MailMessageDataCollector collector) {
        this.recipient = collector.getRecipient();
        this.subject = collector.getSubject();
        this.text = collector.getText();
        this.resources = null;
        if (collector.getResources() != null) {
            this.resources = new LinkedList<>(collector.getResources());
        }
    }
}