package application.data.promo.service;

import application.data.promo.Promo;
import application.data.promo.models.PromoType;
import application.data.promo.repository.PromoRepositoryImplementation;
import application.data.users.attributes.Role;
import application.data.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PromoService {
    private PromoRepositoryImplementation promoRepository;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPromoRepository(PromoRepositoryImplementation promoRepository) {
        this.promoRepository = promoRepository;
    }

    public List<Promo> getAllPromos() {
        return promoRepository.getAllPromos();
    }

    public Promo getPromoById(Long id) {
        return promoRepository.getPromoById(id);
    }

    public Promo getPromoByCode(String code) {
        return promoRepository.getPromoByCode(code);
    }

    public void updatePromo(Promo promo) {
        promoRepository.updatePromo(promo);
    }

    public void deletePromo(Long id) {
        promoRepository.deletePromo(id);
    }

    public synchronized Promo savePromo(Promo promo) {
        return promoRepository.savePromo(promo);
    }

    public Promo generateNewPromo(PromoType type) {
        Promo promo = promoRepository.savePromo(new Promo(0L , UUID.randomUUID().toString().toUpperCase() , type));
        return promo;
    }

    public void usePromo(String code , String mail) {
        Promo promo = getPromoByCode(code);
        switch (promo.getType()) {
            case CHANGE_ROLE_TO_USER:
                userService.updateUserRoleForCurrentUser(mail , Role.ROLE_USER);
                break;
            case CHANGE_ROLE_TO_ADMIN:
                userService.updateUserRoleForCurrentUser(mail , Role.ROLE_ADMIN);
                break;
        }
    }
}