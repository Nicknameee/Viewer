package application.web.responses.websocket;

import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ChangesAlertArticleResponse extends ApplicationWebResponse {
    private String secret;
}
