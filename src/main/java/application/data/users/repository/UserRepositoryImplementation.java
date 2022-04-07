package application.data.users.repository;

import application.data.users.User;
import application.data.users.security.UserCredentialsCryptTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserRepositoryImplementation {
    private final Logger logger = LoggerFactory.getLogger(UserRepositoryImplementation.class);

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryImplementation(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User saveUser(User user)
    {
        user.setPassword(UserCredentialsCryptTool.encodeCredentials(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUserByMail(String mail) {
        return userRepository.getUserByMail(mail);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public void updateWholeUserDataById(User user) {
        userRepository.updateWholeUserDataById(
                user.getUsername()                              ,
                user.getMail()                                  ,
                UserCredentialsCryptTool
                        .encodeCredentials(user.getPassword())  ,
                user.getRole()                                  ,
                user.getStatus()                                ,
                user.getId()
        );
    }

    public void updateUserLoginTime(String mail) {
        userRepository.updateLoginTime(mail);
    }

    public void updateUserLogoutTime(String mail) {
        userRepository.updateLogoutTime(mail);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user.getMail());
    }
}