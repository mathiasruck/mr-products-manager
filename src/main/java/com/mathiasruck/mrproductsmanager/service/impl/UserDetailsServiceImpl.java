package com.mathiasruck.mrproductsmanager.service.impl;

import static com.mathiasruck.mrproductsmanager.builder.AuthenticationUserDetailsBuilder.getAuthenticationUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return getAuthenticationUserDetails().withDefaultData().build();
    }

}
