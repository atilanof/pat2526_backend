package edu.comillas.icai.gitt.pat.padel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita @PreAuthorize en tus controladores
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/pistaPadel/**", "/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Permite ver la consola H2
                )
                .authorizeHttpRequests(authz -> authz
                        // --- ACCESO PÚBLICO (Sin login) ---
                        .requestMatchers(HttpMethod.POST, "/pistaPadel/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pistaPadel/auth/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/pistaPadel/availability/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pistaPadel/courts").permitAll() // Para que el Frontend vea las pistas

                        // --- ACCESO PROTEGIDO (Requiere login) ---
                        .requestMatchers(HttpMethod.POST, "/pistaPadel/auth/logout").authenticated()
                        .requestMatchers(HttpMethod.GET, "/pistaPadel/auth/me").authenticated()

                        // Cualquier otra petición (Reservar, Admin, Borrar) requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .httpBasic(httpBasic -> {}); // Soporte para pruebas rápidas

        return http.build();
    }
}