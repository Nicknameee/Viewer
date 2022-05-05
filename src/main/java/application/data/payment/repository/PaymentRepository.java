package application.data.payment.repository;

import application.data.payment.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository extends JpaRepository<PaymentModel , Long> {
    @Query("SELECT pm FROM PaymentModel pm WHERE pm.id=:id")
    PaymentModel getPaymentModelById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE PaymentModel pm SET pm.card=:card , pm.iban=:iban , pm.receiver=:receiver WHERE pm.id=:id")
    void updatePaymentModel(@Param("card")      String card ,
                            @Param("iban")      String iban ,
                            @Param("receiver")  String receiver ,
                            @Param("id")        Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM PaymentModel pm WHERE pm.id=:id")
    void deletePaymentModelById(@Param("id") Long id);
}