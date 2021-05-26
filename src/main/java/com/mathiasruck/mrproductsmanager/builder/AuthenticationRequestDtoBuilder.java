package com.mathiasruck.mrproductsmanager.builder;

import com.mathiasruck.mrproductsmanager.dto.AuthenticationRequestDto;

public class AuthenticationRequestDtoBuilder {
    private AuthenticationRequestDto dto;

    public static AuthenticationRequestDtoBuilder getAuthenticationRequestDtoBuilder() {
        AuthenticationRequestDtoBuilder builder = new AuthenticationRequestDtoBuilder();
        builder.dto = new AuthenticationRequestDto();
        return builder;
    }

    public AuthenticationRequestDtoBuilder withUsername(String username) {
        dto.setUsername(username);
        return this;
    }

    public AuthenticationRequestDtoBuilder withPassword(String password) {
        dto.setPassword(password);
        return this;
    }

    public AuthenticationRequestDto build() {
        return dto;
    }
}
