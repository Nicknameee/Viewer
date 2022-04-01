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

    @Column(name = "username" , unique = true)
    private String username;

    @Column(name = "mail" , unique = true)
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "last_seen")
    @Temporal(TemporalType.DATE)
    private Date lastSeen;

    @Column(name = "role")
    @ColumnDefault(value = "USER")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @ColumnDefault(value = "ACTIVE")
    @Enumerated(EnumType.STRING)
    private Status status;
}