package ru.edu.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(

                    "/",
                    "/api/create",
                    "/registration",
                    "/create/account",
                    "/help",
                    "/helpEU",
                    "/report",
                    "/api/report",
                    "/api/user/me"


                ).permitAll()
                .requestMatchers("/admin/*", "/admin/api/**").hasAnyRole("ADMIN")
                .anyRequest().hasAnyRole("USER", "ADMIN")


            )

            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout((logout) -> logout
                .logoutSuccessUrl("/logout")
                .permitAll()
            )

        ;

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/styles/**").requestMatchers("/image/**");
    }
}