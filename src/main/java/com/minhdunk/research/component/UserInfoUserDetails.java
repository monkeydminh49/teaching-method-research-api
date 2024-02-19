package com.minhdunk.research.component;

import com.minhdunk.research.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoUserDetails implements UserDetails {

    @Getter
    private final String firstname;
    @Getter
    private final String lastname;
    @Getter
    private final String email;
    @Getter
    private final Long id;
    private final String username;
    private final String password;
    @Getter
    private final User user;

    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(User user) {
        this.user = user;
        this.email = user.getEmail();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
//        this.authorities = user.getRoles().stream().map(
//                role -> new SimpleGrantedAuthority(role.name())
//        ).collect(Collectors.toList());
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
