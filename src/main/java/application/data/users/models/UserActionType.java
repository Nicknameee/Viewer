package application.data.users.models;

public enum UserActionType {
    RESTORE_PASSWORD(1) , CONFIRM_REGISTRATION(2) , CONFIRM_AUTHORIZATION(3);

    private Integer key;

    UserActionType(Integer key) {
        this.key = key;
    }
}