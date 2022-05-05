package application.data.payment.models;

import lombok.Getter;

@Getter
public enum Bank {
    PRIVAT_BANK("PrivatBank") , SENSE("Sense SuperApp") , RAIFFEISEN("Raiffeisen Bank Aval") , MONOBANK("Monobank");

    private final String naming;

    Bank(String naming) {
        this.naming = naming;
    }
}
