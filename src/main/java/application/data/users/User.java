package application.data.users;

import application.data.users.attributes.Role;
import application.data.users.attributes.Status;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
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

    @Column(name = "last_seen" , nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastSeen;

    @Column(name = "role" , nullable = false)
    //@ColumnDefault(value = "ROLE_USER")
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @Column(name = "status" , nullable = false)
    //@ColumnDefault(value = "ACTIVE")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ENABLE;
}