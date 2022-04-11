package application.data.users.repository;

import application.data.users.User;
import application.data.users.attributes.Role;
import application.data.users.attributes.Status;
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
    @Query("UPDATE User u SET u.username=:username , u.mail=:mail , u.password=:password , u.role=:role , u.status=:status WHERE u.id=:id")
    void updateWholeUserDataById(
                             @Param("username") String username ,
                             @Param("mail")     String mail     ,
                             @Param("password") String password ,
                             @Param("role")     String role       ,
                             @Param("status")   Status status   ,
                             @Param("id")       Long id);

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
    @Query("DELETE FROM User u WHERE u.mail=:mail")
    void deleteUser(@Param("mail") String mail);
}