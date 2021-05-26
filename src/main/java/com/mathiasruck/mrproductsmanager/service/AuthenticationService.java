package com.mathiasruck.mrproductsmanager.service;

import com.mathiasruck.mrproductsmanager.model.AuthenticationRequest;
import com.mathiasruck.mrproductsmanager.model.AuthenticationResponse;

public interface AuthenticationService {

    public AuthenticationResponse validateAuthentication(AuthenticationRequest authenticationRequest);

}
