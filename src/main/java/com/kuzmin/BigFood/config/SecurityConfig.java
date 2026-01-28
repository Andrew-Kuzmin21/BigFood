package com.kuzmin.BigFood.config;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((request) -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/home","/error","/login","/registration", "/registrationEmpl").permitAll()
                        .requestMatchers(
                                "/part_order/**",
                                "/customers",
                                "/employees",
                                "/position",
                                "/parts/**",
                                "/part-orders/**"
                        )
                        .hasAnyAuthority("ROLE_Admin", "ROLE_User")

                        .requestMatchers("/breakage-types/**", "/repair-orders/**")
                        .hasAnyAuthority( "ROLE_Customer", "ROLE_Mechanic","ROLE_Admin", "ROLE_PartsManager")

                        .requestMatchers("/repair-orders", "/part_order")
                        .hasAnyAuthority( "ROLE_Mechanic")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(CsrfConfigurer::disable)
                .build();
    }

}
