package com.Project.Backend.config.token;

import com.Project.Backend.service.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenService tokenService;

    public TokenFilterConfigurer(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void configure(HttpSecurity Http){
        TokenFilter filter = new TokenFilter(tokenService);
        Http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }
}
