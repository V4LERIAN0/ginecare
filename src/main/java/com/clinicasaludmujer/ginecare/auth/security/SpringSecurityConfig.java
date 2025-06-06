package com.clinicasaludmujer.ginecare.auth.security;

import com.clinicasaludmujer.ginecare.auth.filter.JwtAuthenticationFilter;
import com.clinicasaludmujer.ginecare.auth.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;import java.util.Arrays;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    ///SE AÑADE LA SEGUIRDAD PARA ACCEDER A LAS URL DE LOS ENDPOINTS EXPUESTOS

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Rutas de usuarios
                        .requestMatchers(HttpMethod.GET, "/users", "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyRole("ADMIN")

                        // Rutas de Pacientes
                        .requestMatchers(HttpMethod.GET, "/pacientes", "/pacientes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pacientes").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/pacientes/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pacientes/{id}").hasAnyRole("ADMIN")

                        //Ruta Consultas
                        .requestMatchers(HttpMethod.GET, "/consultas", "/consultas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/consultas/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/consultas").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/consultas/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/consultas/{id}").hasAnyRole("ADMIN")

                        //Ruta Diagnostico
                        .requestMatchers(HttpMethod.GET, "/diagnosticos", "/diagnosticos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/diagnosticos/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/diagnosticos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/diagnosticos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/diagnosticos/{id}").hasAnyRole("ADMIN")

                        //Ruta Expediente
                        .requestMatchers(HttpMethod.GET, "/expedientes", "/expedientes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/expedientes/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/expedientes").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/expedientes/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/expedientes/{id}").hasAnyRole("ADMIN")

                        //Ruta Cita Medica
                        .requestMatchers(HttpMethod.GET, "/citas", "/citas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/citas/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/citas").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/citas/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/citas/{id}").hasAnyRole("ADMIN")

                        //Ruta Medicamentos
                        .requestMatchers(HttpMethod.GET, "/medicamentos", "/medicamentos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/medicamentos/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/medicamentos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/medicamentos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/medicamentos/{id}").hasAnyRole("ADMIN")

                        //Ruta Procedimiento Médico
                        .requestMatchers(HttpMethod.GET, "/procedimientos", "/procedimientos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/procedimientos/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/procedimientos").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/procedimientos/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/procedimientos/{id}").hasAnyRole("ADMIN")

                        //Ruta Receta
                        .requestMatchers(HttpMethod.GET, "/recetas", "/recetas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/recetas/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/recetas").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/recetas/{id}").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/recetas/{id}").hasAnyRole("ADMIN")

                        // Rutas de Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:9090"));
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

