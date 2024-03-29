package application.data.users.service;

import application.data.users.User;
import application.data.users.attributes.Role;
import application.data.users.attributes.Status;
import application.data.users.models.Language;
import application.data.users.repository.UserRepositoryImplementation;
import application.data.users.session.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserRepositoryImplementation userRepository;

    private final UserSessionService sessionService;

    @Autowired
    UserService(UserRepositoryImplementation userRepositoryImplementation,
                UserSessionService sessionService) {
        this.userRepository = userRepositoryImplementation;
        this.sessionService = sessionService;
    }

    public User saveUser(User user) {
        return userRepository.saveUser(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.getAllUsers();
    }

    public List<User> getAllUsersExceptCurrent() {
        return userRepository.getAllUsersExceptCurrent();
    }

    public User getUserByMail(String mail) {
        return userRepository.getUserByMail(mail);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void updatePassword(User user) {
        userRepository.updatePassword(user);
    }

    public void updateUserLoginTime(String mail) {
        userRepository.updateUserLoginTime(mail);
    }

    public void updateUserLogoutTime(String mail) {
        userRepository.updateUserLogoutTime(mail);
    }

    public void updateUserRoleForCurrentUser(String mail , Role role) {
        User user = userRepository.getUserByMail(mail);
        if (user.getRole().equals(role)) {
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new LinkedList<>(new ArrayList<>(role.getAuthorities()));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        userRepository.updateUserRole(mail , role);
    }

    public void updateUserRoleAndStatus(String mail , Role role , Status status) {
        User user = userRepository.getUserByMail(mail);
        if (user.getStatus().equals(status) && user.getRole().equals(role)) {
            return;
        }
        userRepository.updateUserRole(mail , role);
        userRepository.updateUserStatus(mail , status);
        UserDetails userByMail = sessionService.findAllLoggedInUsers()
                .stream()
                .filter(u -> u.getUsername().equals(mail))
                .findAny().orElse(null);
        if (userByMail != null) {
            SessionRegistry registry = sessionService.getSessionRegistry();
            registry.getAllSessions(userByMail , false)
            .forEach(sessionInformation -> {
                registry.removeSessionInformation(sessionInformation.getSessionId());
                sessionInformation.expireNow();
            });
        }
    }

    public void updateUserLanguage(String mail , Language language) {
        userRepository.updateUserLanguage(mail , language);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public Boolean checkSession(String mail) {
        UserDetails userDetails = sessionService.findAllLoggedInUsers()
                .stream()
                .filter(user -> user.getUsername().equals(mail))
                .findAny().orElse(null);
        return userDetails != null;
    }

    public User checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return getUserByMail(authentication.getName());
        }
        return null;
    }
}