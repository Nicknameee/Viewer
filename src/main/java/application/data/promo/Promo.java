package application.data.promo;

import application.data.promo.models.PromoType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "promo")
@AllArgsConstructor
@NoArgsConstructor
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code" , unique = true , nullable = false)
    private String code;

    @Column(name = "type" , nullable = false)
    @Enumerated(EnumType.STRING)
    private PromoType type;
}