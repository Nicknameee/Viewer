package application.data.users.service;

import application.data.users.User;
import application.data.users.repository.UserRepositoryImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        user.setLastSeen(new Date());
        return userRepository.saveUser(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.getAllUsers();
    }

    public User getUserByMail(User user) {
        return userRepository.getUserByMail(user.getMail());
    }

    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }
}