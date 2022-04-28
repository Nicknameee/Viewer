package application.data.users.security;

import application.data.users.User;
import application.data.users.repository.UserRepositoryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service("userDetailsImplementationService")
public class UserDetailsImplementationService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(UserDetailsImplementationService.class);

    private final UserRepositoryImplementation userRepositoryImplementationEntity;

    @Autowired
    UserDetailsImplementationService(UserRepositoryImplementation userRepositoryImplementationEntity) {
        this.userRepositoryImplementationEntity = userRepositoryImplementationEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String mail)
            throws UsernameNotFoundException {
        User user = userRepositoryImplementationEntity.getUserByMail(mail);
        if (user == null) {
            throw new UsernameNotFoundException("User with that credentials was not found");
        }
        return UserSecurityConverter.convertUser(user);
    }
}
