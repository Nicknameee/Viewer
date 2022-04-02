package application.data.verification;

import application.data.users.models.UserActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "verification")
@NoArgsConstructor
@AllArgsConstructor
public class VerificationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail" , nullable = false)
    private String mail;

    @Column(name = "action_type" , nullable = false)
    @Enumerated(EnumType.STRING)
    private UserActionType actionType;

    @Column(name = "code" , nullable = false , unique = true)
    private String code;
}