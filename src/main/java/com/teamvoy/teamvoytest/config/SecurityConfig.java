package com.teamvoy.teamvoytest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/iphones").hasRole("MANAGER")
                .antMatchers(HttpMethod.POST, "/orders").hasRole("CLIENT")
                .antMatchers(HttpMethod.POST, "/orders/**").hasRole("CLIENT")
                .antMatchers(HttpMethod.GET, "/iphones").permitAll()
                .antMatchers(HttpMethod.GET, "/orders").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("client").password("{noop}1111").roles("CLIENT")
                .and()
                .withUser("manager").password("{noop}1111").roles("MANAGER");
    }
}
