package com.mathiasruck.mrproductsmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mathiasruck.mrproductsmanager.AuthenticationRequestDto;
import com.mathiasruck.mrproductsmanager.model.AuthenticationResponse;
import com.mathiasruck.mrproductsmanager.service.AuthenticationService;

@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/v1/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws UsernameNotFoundException {
        AuthenticationResponse response = authenticationService.validateAuthentication(authenticationRequestDto.getAuthenticationRequest());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}