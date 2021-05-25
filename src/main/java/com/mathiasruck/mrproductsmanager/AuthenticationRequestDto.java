package com.mathiasruck.mrproductsmanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathiasruck.mrproductsmanager.model.AuthenticationRequest;

public class AuthenticationRequestDto {

    private AuthenticationRequest authenticationRequest;

    public AuthenticationRequestDto() {
        this.authenticationRequest = new AuthenticationRequest();
    }

    public String getUsername() {
        return authenticationRequest.getUsername();
    }

    public void setUsername(String username) {
        authenticationRequest.setUsername(username);
    }

    public String getPassword() {
        return authenticationRequest.getPassword();
    }

    public void setPassword(String password) {
        authenticationRequest.setPassword(password);
    }

    @JsonIgnore
    public AuthenticationRequest getAuthenticationRequest() {
        return authenticationRequest;
    }
}
