package application.data.promo.repository;

import application.data.promo.Promo;
import application.data.promo.models.PromoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PromoRepository extends JpaRepository<Promo, Long> {
    @Query("SELECT p FROM Promo p WHERE p.id=:id")
    Promo getPromoById(@Param("id") Long id);

    @Query("SELECT p FROM Promo p WHERE p.code=:code")
    Promo getPromoByCode(@Param("code") String code);

    @Modifying
    @Transactional
    @Query("UPDATE Promo p SET p.code=:code , p.type=:type WHERE p.id=:id")
    void updatePromo(@Param("code") String code     ,
                     @Param("type") PromoType type  ,
                     @Param("id")   Long id         );

    @Modifying
    @Transactional
    @Query("DELETE FROM Promo p WHERE p.id=:id")
    void deletePromo(@Param("id") Long id);
}