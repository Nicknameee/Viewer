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
    @Query("UPDATE User u SET u.password=:password WHERE u.mail=:mail")
    void updatePassword(@Param("password") String password ,
                        @Param("mail")     String mail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.loginTime=current_timestamp WHERE u.mail=:mail")
    void updateLoginTime(@Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.logoutTime=current_timestamp WHERE u.mail=:mail")
    void updateLogoutTime(@Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.role=:role WHERE u.mail=:mail")
    void updateUserRole(@Param("role") String role , @Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status=:status WHERE u.mail=:mail")
    void updateUserStatus(@Param("status") String status , @Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.mail=:mail")
    void deleteUser(@Param("mail") String mail);
}