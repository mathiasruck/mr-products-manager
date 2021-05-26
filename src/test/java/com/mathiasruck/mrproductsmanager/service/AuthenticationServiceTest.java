package com.mathiasruck.mrproductsmanager.service;

import static com.mathiasruck.mrproductsmanager.builder.AuthenticationRequestDtoBuilder.getAuthenticationRequestDtoBuilder;
import static com.mathiasruck.mrproductsmanager.builder.AuthenticationUserDetailsBuilder.getAuthenticationUserDetails;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mathiasruck.mrproductsmanager.exception.MrProductManagerException;
import com.mathiasruck.mrproductsmanager.model.AuthenticationRequest;
import com.mathiasruck.mrproductsmanager.model.AuthenticationResponse;
import com.mathiasruck.mrproductsmanager.model.AuthenticationUserDetails;
import com.mathiasruck.mrproductsmanager.service.impl.AuthenticationServiceImpl;
import com.mathiasruck.mrproductsmanager.util.JwtUtil;

public class AuthenticationServiceTest {

    AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        initMocks(this);
        authenticationService = new AuthenticationServiceImpl();
        setField(authenticationService, "authenticationManager", authenticationManager);
        setField(authenticationService, "jwtTokenUtil", jwtTokenUtil);
        setField(authenticationService, "userDetailsService", userDetailsService);
    }

    @Test
    public void userNotFoundException() {
        exceptionRule.expect(MrProductManagerException.class);
        exceptionRule.expectMessage("user_not_found");
        AuthenticationRequest authenticationRequest = getAuthenticationRequestDtoBuilder()
                .withUsername("username")
                .withPassword("password")
                .build()
                .getAuthenticationRequest();

        authenticationService.validateAuthentication(authenticationRequest);
    }

    @Test
    public void successfullyGenerateToken() {
        AuthenticationRequest authenticationRequest = getAuthenticationRequestDtoBuilder()
                .withUsername("username")
                .withPassword("password")
                .build()
                .getAuthenticationRequest();

        AuthenticationUserDetails authenticationUserDetails = getAuthenticationUserDetails().withDefaultData().build();
        when(userDetailsService.loadUserByUsername(Mockito.anyString())).thenReturn(authenticationUserDetails);

        when(jwtTokenUtil.generateToken(Mockito.any(AuthenticationUserDetails.class))).thenCallRealMethod();
        AuthenticationResponse response = authenticationService.validateAuthentication(authenticationRequest);
        assertThat(response.getId(), is(equalTo(1)));
        assertThat(response.getUsername(), is(equalTo("user")));
        assertThat(response.getRoles().get(0), is(equalTo("ROLE_ADMIN")));
    }
}
