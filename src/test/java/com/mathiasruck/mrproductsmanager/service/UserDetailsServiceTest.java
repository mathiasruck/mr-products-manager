package com.mathiasruck.mrproductsmanager.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mathiasruck.mrproductsmanager.service.impl.UserDetailsServiceImpl;

public class UserDetailsServiceTest {

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        userDetailsService = new UserDetailsServiceImpl();
    }

    @Test
    public void successfullyLoadUser() {
        UserDetails username = userDetailsService.loadUserByUsername("username");
        GrantedAuthority authority = username.getAuthorities().stream().findFirst().get();
        assertThat(authority.getAuthority(), is(equalTo("ROLE_ADMIN")));
        assertThat(username.isEnabled(), is(true));
        assertThat(username.getUsername(), is(equalTo("user")));
        assertThat(BCRYPT_PATTERN.matcher(username.getPassword()).matches(), is(true));
    }
}
