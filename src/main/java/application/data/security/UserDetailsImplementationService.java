package application.data.security;

import application.data.users.User;
import application.data.users.repository.UserRepositoryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
        return UserSecurityConverter.convertUser(user);
    }
}
