package com.example.heroesbackend.security.service;

import com.example.heroesbackend.security.entity.User;
import com.example.heroesbackend.security.entity.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username)
                .orElseGet(() -> userService.getUserByEmail(username).get());

        List<GrantedAuthority> authorities =
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role
                        .getRolName().name())).collect(Collectors.toList());

        UserPrincipal userPrincipal = new UserPrincipal(user);
        userPrincipal.setAuthorities(authorities);

        return userPrincipal;
    }
}
