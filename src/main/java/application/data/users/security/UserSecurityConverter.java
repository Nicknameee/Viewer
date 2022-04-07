package application.data.users.security;

import application.data.users.User;
import application.data.users.attributes.Status;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class UserSecurityConverter implements UserDetails {
    private final String username;
    private final String password;
    private final Set<SimpleGrantedAuthority> authorities;
    private final Boolean isActive;

    public UserSecurityConverter(
            String username,
            String password,
            Set<SimpleGrantedAuthority> authorities,
            Boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public static UserDetails convertUser(User user) {
        return
                new org.springframework.security.core.userdetails.User(
                        user.getMail() , user.getPassword(),
                        user.getStatus().equals(Status.ENABLE),
                        user.getStatus().equals(Status.ENABLE),
                        user.getStatus().equals(Status.ENABLE),
                        user.getStatus().equals(Status.ENABLE),
                        user.getRole().getAuthorities()
                );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}