package com.mathiasruck.mrproductsmanager.controller;

import static com.mathiasruck.mrproductsmanager.builder.AuthenticationResponseBuilder.getAuthenticationResponse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathiasruck.mrproductsmanager.builder.AuthenticationRequestDtoBuilder;
import com.mathiasruck.mrproductsmanager.dto.AuthenticationRequestDto;
import com.mathiasruck.mrproductsmanager.model.AuthenticationResponse;
import com.mathiasruck.mrproductsmanager.service.AuthenticationService;

@WebMvcTest(controllers = { AuthenticationController.class }, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void shouldAuthenteticateSuccessfully() throws Exception {
        AuthenticationRequestDto dto = AuthenticationRequestDtoBuilder.getAuthenticationRequestDtoBuilder()
                .withUsername("username")
                .withPassword("password")
                .build();
        AuthenticationResponse response = getAuthenticationResponse()
                .withId(1)
                .withUsername("user")
                .withRole("ROLE_ADMIN")
                .build();

        String dtoAsJson = new ObjectMapper().writeValueAsString(dto);
        when(authenticationService.validateAuthentication(Mockito.any())).thenReturn(response);

        this.mockMvc.perform(post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoAsJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.roles.[0]").value("ROLE_ADMIN"));
    }

}
