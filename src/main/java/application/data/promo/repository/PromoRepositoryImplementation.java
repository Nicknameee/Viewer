package application.data.promo.repository;

import application.data.promo.Promo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PromoRepositoryImplementation {
    private PromoRepository promoRepository;

    @Autowired
    public void setPromoRepository(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    public List<Promo> getAllPromos() {
        return promoRepository.findAll();
    }

    public Promo getPromoById(Long id) {
        return promoRepository.getPromoById(id);
    }

    public Promo getPromoByCode(String code) {
        return promoRepository.getPromoByCode(code);
    }

    public Promo savePromo(Promo promo) {
        return promoRepository.save(promo);
    }

    public void updatePromo(Promo promo) {
        promoRepository.updatePromo(promo.getCode() , promo.getType() , promo.getId());
    }

    public void deletePromo(Long id) {
        promoRepository.deletePromo(id);
    }
}