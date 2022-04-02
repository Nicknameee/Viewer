package application.data.users.repository;

import application.data.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.mail=:mail")
    User getUserByMail(@Param("mail") String mail);

    @Query("SELECT u FROM User u WHERE u.username=:username")
    User getUserByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username=:username , u.password=:password , u.status=:status WHERE u.mail=:mail")
    void updateUser(
            @Param("username")  String username ,
            @Param("password")  String password ,
            @Param("status")    String status   ,
            @Param("mail")      String mail
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.mail=:mail")
    void deleteUser(@Param("mail") String mail);
}