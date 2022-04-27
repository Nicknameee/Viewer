package application.data.users.service;

import application.data.users.User;
import application.data.users.attributes.Role;
import application.data.users.repository.UserRepositoryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepositoryImplementation userRepository;

    @Autowired
    UserService(UserRepositoryImplementation userRepositoryImplementation) {
        this.userRepository = userRepositoryImplementation;
    }

    public User saveUser(User user)
    {
        return userRepository.saveUser(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.getAllUsers();
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

    public void updateUserRole(String mail , Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new LinkedList<>(new ArrayList<>(role.getAuthorities()));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        userRepository.updateUserRole(mail , role);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }
}