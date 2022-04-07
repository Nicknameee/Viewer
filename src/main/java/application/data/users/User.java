package application.data.users;

import application.data.users.attributes.Role;
import application.data.users.attributes.Status;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" , nullable = false , unique = true)
    private String username;

    @Column(name = "mail" , nullable = false , unique = true)
    private String mail;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "login_time" , nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp loginTime = new Timestamp(Calendar.getInstance().getTime().getTime());

    @Column(name = "logout_time" , nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp logoutTime;

    @Column(name = "role" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Column(name = "status" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ENABLE;
}