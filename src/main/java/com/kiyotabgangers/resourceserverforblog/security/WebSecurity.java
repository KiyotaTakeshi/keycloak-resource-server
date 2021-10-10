package com.kiyotabgangers.resourceserverforblog.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true
)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // spring security を入れただけの状態のデフォルトの認証方式は以下の実装にある
        // org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.java#configure(HttpSecurity http)
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/**")
                // scope based access control
                // .hasAuthority("SCOPE_profile")
                .hasRole("developer")
                // .hasAnyRole("developer", "user")
                // .hasAuthority("ROLE_developer")
                // .hasAnyAuthority("ROLE_developer")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
    }
}
