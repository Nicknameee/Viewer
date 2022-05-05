package application.web.responses.manager;

import application.data.payment.PaymentModel;
import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PaymentResponse extends ApplicationWebResponse {
    private PaymentModel payment;
}
