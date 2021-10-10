package com.kiyotabgangers.resourceserverforblog.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // spring security を入れただけの状態のデフォルトの認証方式は以下の実装にある
        // org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.java#configure(HttpSecurity http)
        http
                .authorizeRequests()
                    // scope based access control
                    .antMatchers(HttpMethod.GET, "/users/**").hasAuthority("SCOPE_profile")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}
