package com.minhdunk.research.service;

import com.minhdunk.research.component.UserInfoUserDetails;
import com.minhdunk.research.entity.User;
import com.minhdunk.research.exception.EmailNotVerifiedException;
import com.minhdunk.research.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByUsername(username);
        User user = userInfo.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
        if (!user.getEnabled()) {
            throw new EmailNotVerifiedException("Email not verified");
        }

        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }

}
