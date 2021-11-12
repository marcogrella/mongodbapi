package com.br.mongoapi.config;

import com.br.mongoapi.service.ApiUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private ApiUserDetailsService apiUserDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(ApiUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.apiUserDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

         auth.inMemoryAuthentication()
                 .withUser("admin")
             .password(bCryptPasswordEncoder.encode("admin"))
                .roles("USER", "ADMIN");
                        //         .and()
                        //         .withUser("user")
                        //          .password(passwordEncoder.encode("user"))
                        //         .roles("USER");


        auth.userDetailsService(apiUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                 .authorizeRequests()
                .antMatchers("/student/**").hasRole("ADMIN")
                // .antMatchers("/user/**").permitAll()
                .antMatchers("/user/**").hasRole("SUPER_ADMIN")
                .antMatchers("/actuator/**").permitAll()
                .anyRequest()
                .authenticated()
                //.and()
                //.formLogin()
                .and()
                .httpBasic();

    }
}
