package application.web.configuration;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class SecuritySuccessHandlerEntity extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public SecuritySuccessHandlerEntity() {
        super();
        setUseReferer(true);
    }
}