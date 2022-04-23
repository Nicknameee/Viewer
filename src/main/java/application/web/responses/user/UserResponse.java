package application.web.responses.user;

import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserResponse extends ApplicationWebResponse {
    public UserResponse(Boolean success , String error) {
        super(success , error);
    }
}