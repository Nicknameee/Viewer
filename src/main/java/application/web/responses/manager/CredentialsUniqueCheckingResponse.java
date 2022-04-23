package application.web.responses.manager;

import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CredentialsUniqueCheckingResponse extends ApplicationWebResponse {
    private Boolean isUnique;
}
