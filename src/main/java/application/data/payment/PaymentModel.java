package application.data.payment;

import application.data.payment.models.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card" , nullable = false)
    private String card;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Bank bank;

    @Column(name = "receiver")
    private String receiver;
}