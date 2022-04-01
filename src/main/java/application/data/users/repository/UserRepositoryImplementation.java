package application.data.users.repository;

import application.data.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUserByMail(String mail) {
        return userRepository.getUserByMail(mail);
    }

    public User updateUser(User user) {
        userRepository.updateUser(
                user.getUsername()      ,
                user.getPassword()      ,
                user.getStatus().name() ,
                user.getMail()
        );
        return user;
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user.getMail());
    }
}