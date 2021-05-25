package com.mathiasruck.mrproductsmanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mathiasruck.mrproductsmanager.exception.ProductManagerException;
import com.mathiasruck.mrproductsmanager.model.AuthenticationRequest;
import com.mathiasruck.mrproductsmanager.model.AuthenticationResponse;
import com.mathiasruck.mrproductsmanager.model.AuthenticationUserDetails;
import com.mathiasruck.mrproductsmanager.service.AuthenticationService;
import com.mathiasruck.mrproductsmanager.util.JwtUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public AuthenticationResponse validateAuthentication(AuthenticationRequest authenticationRequest) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            final AuthenticationUserDetails userDetails = (AuthenticationUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);

            AuthenticationResponse response = new AuthenticationResponse(jwt);
            response.setId(userDetails.getId());
            response.setUsername(userDetails.getUsername());

            List<String> roles = new ArrayList<>();
            userDetails.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
            response.setRoles(roles);
            return response;
        } catch (Exception e) {
            throw new ProductManagerException("user_not_found");
        }
    }
}
