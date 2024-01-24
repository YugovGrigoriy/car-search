package ru.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // todo: почитать и понять
    /*
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                    "/",
                    "/car-search-engine",
                    "/car-search",
                    "/register",
                    "/registration",
                    "/personal-area"
                )
                .permitAll()
                .requestMatchers("/main/*").authenticated()
            );

        http.formLogin((form) -> form
            .loginPage("/login")
            .defaultSuccessUrl("/main/engine", true)
            .permitAll()
        );

        http.logout((logout) -> logout
            .logoutSuccessUrl("/")
            .permitAll()
        );

        return http.build();
    }

     */


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                    "/",
                    "/car-search-engine",
                    "/car-search",
                    "/register",
                    "/registration",
                    "/personal-area"


                ).permitAll()

                .requestMatchers("/main/*").authenticated()
            )

            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/main/engine", true)
                .permitAll()
            )

            .logout((logout) -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )

        ;

        return http.build();
    }
    //todo подумать над шифрованием
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/styles/**").requestMatchers("/image/**");
    }
}