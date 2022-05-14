package application.data.promo.repository;

import application.data.promo.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query("SELECT p FROM Promo p WHERE p.code=:code")
    Promo getPromoByCode(@Param("code") String code);

    @Transactional
    @Modifying
    @Query("DELETE FROM Promo p WHERE p.id=:id")
    void deletePromo(@Param("id") Long id);
}