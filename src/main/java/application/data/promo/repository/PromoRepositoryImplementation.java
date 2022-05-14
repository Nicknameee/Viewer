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

    public List<Promo> findAll() {
        return promoRepository.findAll();
    }

    public Promo getPromoByCode(String code) {
        return promoRepository.getPromoByCode(code);
    }

    public Promo savePromo(Promo promo) {
        return promoRepository.save(promo);
    }

    public void deletePromo(Long id) {
        promoRepository.deletePromo(id);
    }
}