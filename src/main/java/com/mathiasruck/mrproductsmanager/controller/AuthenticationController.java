package com.mathiasruck.mrproductsmanager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mathiasruck.mrproductsmanager.model.AuthenticationRequest;
import com.mathiasruck.mrproductsmanager.model.AuthenticationResponse;
import com.mathiasruck.mrproductsmanager.model.MyUserDetails;
import com.mathiasruck.mrproductsmanager.util.JwtUtil;

@RestController
@RequestMapping
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    // @CrossOrigin
    @PostMapping(value = "/v1/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // if authentication was succesful else throw an exception
        final MyUserDetails userDetails = (MyUserDetails) userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        AuthenticationResponse response = new AuthenticationResponse(jwt);

        response.setId(userDetails.getId());
        response.setUsername(userDetails.getUsername());
        List<String> roles = new ArrayList<String>();
        userDetails.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
        response.setRoles(roles);

        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);

    }

    @GetMapping(value = "/hello")
    // @CrossOrigin
    // @GetMapping(value = "/login")
    public ResponseEntity<?> greetHello() throws Exception {
        // public ResponseEntity<?> createAuthenticationToken() throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>("Helloworld", responseHeaders, HttpStatus.OK);
    }
}
