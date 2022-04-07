package application.data.users.service;

import application.data.users.User;
import application.data.users.repository.UserRepositoryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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

    public void updateWholeUserData(User user) {
        userRepository.updateWholeUserDataById(user);
    }

    public void updateUserLoginTime(String mail) {
        userRepository.updateUserLoginTime(mail);
    }

    public void updateUserLogoutTime(String mail) {
        userRepository.updateUserLogoutTime(mail);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }


}