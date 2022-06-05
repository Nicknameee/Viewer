package application.data.users.listeners;

import application.data.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        if (event.getSecurityContexts().size() > 0) {
            userService.updateUserLogoutTime(event.getSecurityContexts().get(0).getAuthentication().getName());
        }
    }
}