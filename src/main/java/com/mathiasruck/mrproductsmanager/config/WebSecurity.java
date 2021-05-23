package com.mathiasruck.mrproductsmanager.config;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity {

    @Order(1)
    @Configuration
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().authorizeRequests().antMatchers("/prometheus").authenticated()
                    .and().httpBasic()
                    .and().exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN));
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("user").password("{noop}" + "passord").authorities(new SimpleGrantedAuthority("metric"));
        }

    }

    @Configuration
    public static class DefaultWebSecurity extends WebSecurityConfigurerAdapter {

        private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//        @Autowired
//        private JWTAuthorizationFilter authorizationFilter;

        @Autowired
        private UserDetailsService userDetailsService;

        private final String[] PUBLIC_MATCHERS = { "/", "/swagger-ui.css", "/swagger-ui-bundle.js", "/swagger-ui-standalone-preset.js", "/v2/api-docs", "/health", "/v1/accessControl/events/**" };

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedMethod("*");
                corsConfiguration.addAllowedHeader("*");
                corsConfiguration.addAllowedOrigin("*");
                return corsConfiguration;
            })
                    .and().csrf().disable().authorizeRequests()
                    .antMatchers(GET, PUBLIC_MATCHERS).permitAll()
                    .anyRequest().authenticated()
                    .and()
//                    .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    // this disables session creation on Spring Security
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        }

    }
}
