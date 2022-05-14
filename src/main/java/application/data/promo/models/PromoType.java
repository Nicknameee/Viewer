package application.data.promo.models;

public enum PromoType {
    CHANGE_ROLE_TO_ADMIN("Code for changing user role to ADMIN"),
    CHANGE_ROLE_TO_MODERATOR("Code for changing user role to MODERATOR"),
    CHANGE_ROLE_TO_USER("Code for changing user role to USER");

    private String description;

    PromoType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}