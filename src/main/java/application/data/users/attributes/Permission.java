package application.data.users.attributes;

import lombok.Getter;

@Getter
public enum Permission {
    CREATE_USER          ("access:user:create"),
    READ_USER            ("access:user:read"),
    UPDATE_USER          ("access:user:update"),
    DELETE_USER          ("access:user:delete"),
    CREATE_MODERATOR     ("access:moderator:create"),
    READ_MODERATOR       ("access:moderator:read"),
    UPDATE_MODERATOR     ("access:moderator:update"),
    DELETE_MODERATOR     ("access:moderator:delete"),
    CREATE_ADMIN         ("access:admin:create"),
    READ_ADMIN           ("access:admin:read"),
    UPDATE_ADMIN         ("access:admin:update"),
    DELETE_ADMIN         ("access:admin:delete");

    private final String permission;

    Permission(String permission)
    {
        this.permission = permission;
    }
}