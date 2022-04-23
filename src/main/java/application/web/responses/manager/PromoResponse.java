package application.web.responses.manager;

import application.data.promo.Promo;
import application.web.responses.ApplicationWebResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PromoResponse extends ApplicationWebResponse {
    private Promo promo;
}