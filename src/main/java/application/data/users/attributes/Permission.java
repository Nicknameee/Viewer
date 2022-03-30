package application.data.users.attributes;

import lombok.Getter;

@Getter
public enum Permission
{
    CREATE  ("access:create")   ,
    READ    ("access:read")     ,
    UPDATE  ("access:update")   ,
    DELETE  ("access:delete")   ;

    private final String permission;

    Permission(String permission)
    {
        this.permission = permission;
    }
}