package application.data.promo.models;

public enum PromoType {
    CHANGE_ROLE_TO_ADMIN("ROLE_ADMIN") , CHANGE_ROLE_TO_USER("ROLE_USER");

    private String promo;

    PromoType(String promo) {
        this.promo = promo;
    }
}