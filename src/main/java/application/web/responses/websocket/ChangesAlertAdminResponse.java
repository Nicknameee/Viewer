package application.web.responses.websocket;

import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ChangesAlertAdminResponse extends ApplicationWebResponse {
    private String mail;
    private String action;
}
