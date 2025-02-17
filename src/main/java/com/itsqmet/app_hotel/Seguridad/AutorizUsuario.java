package com.itsqmet.app_hotel.Seguridad;

import com.itsqmet.app_hotel.Servicio.UserDetailsServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class AutorizUsuario {

    @Autowired
    private UserDetailsServices userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/formularioCliente", "/registrarCliente", "/login", "/formularioLogin", "/formularioPrestaciones")
                        .permitAll() // Rutas accesibles sin autenticación
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Permitir acceso a archivos estáticos (CSS, JS, imágenes)
                        .requestMatchers("/administrador/**").hasRole("ADMIN")
                        .requestMatchers("/vistaCliente").hasRole("CLIENTE")
                        .requestMatchers("/vistaProveedor").hasRole("PROVEEDOR")
                        .anyRequest().authenticated()  
                )
                .formLogin(form -> form
                        .loginPage("/formularioLogin")
                        .permitAll()
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/postLogin", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/formularioLogin?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}