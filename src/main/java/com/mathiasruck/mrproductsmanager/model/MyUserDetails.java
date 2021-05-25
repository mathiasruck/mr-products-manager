package com.mathiasruck.mrproductsmanager.model;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 275347623L;
    private int id;
    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;
    // new fields for the info
    private String company;
    private String department;

    public MyUserDetails() {
        this.id = 1;
        this.username = "user";
        this.password = "password";
        this.active = true;

        // new fields addition
        this.company = "Company";
        this.department = "Department";

        this.authorities = Arrays.stream(new String[] { "ROLE_ADMIN" })
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}