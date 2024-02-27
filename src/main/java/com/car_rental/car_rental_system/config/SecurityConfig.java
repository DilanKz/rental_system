package com.car_rental.car_rental_system.config;

import com.car_rental.car_rental_system.entity.Admin;
import com.car_rental.car_rental_system.filter.JwtAuthFilter;
import com.car_rental.car_rental_system.repo.AdminRepository;
import com.car_rental.car_rental_system.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Dilan
 * @created 27/02/2024 - 08:30 am
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthFilter jwtFilter;
    private AdminRepository adminRepository;
    private UserRepository userRepository;
    private AuthenticationProvider authenticationProvider;


    public SecurityConfig(JwtAuthFilter jwtFilter, AdminRepository adminRepository, UserRepository userRepository) {
        this.jwtFilter = jwtFilter;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "api/auth/**").permitAll()
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager);

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminRepository.findByUsername(username).orElse(null);
            if (admin == null) {
                com.car_rental.car_rental_system.entity.User user = userRepository.findByUsername(username).orElse(null);

                if (user==null){
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }

                return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities("USER")
                        .build();
            } else {
                return User.builder()
                        .username(admin.getUsername())
                        .password(admin.getPassword())
                        .authorities("ADMIN")
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
