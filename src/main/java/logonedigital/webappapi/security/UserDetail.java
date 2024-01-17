package logonedigital.webappapi.security;

import logonedigital.webappapi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {
    private final String username;
    private final String password;
    private final Boolean isActivated;
    private final Boolean isBlocked;
    private final List<GrantedAuthority> authorities;
    public UserDetail(User user){
      this.username = user.getEmail();
      this.password = user.getPassword();
      this.isActivated = user.getIsActivated();
      this.isBlocked = user.getIsBlocked();
      this.authorities = user.getRoles()
              .stream()
              .map(role -> new SimpleGrantedAuthority(role.getDesignation()))
              .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActivated;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActivated;
    }

    @Override
    public boolean isEnabled() {
        return this.isActivated;
    }
}
