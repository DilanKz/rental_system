package com.car_rental.car_rental_system.config;

import com.car_rental.car_rental_system.entity.Admin;
import com.car_rental.car_rental_system.repo.AdminRepository;
import com.car_rental.car_rental_system.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dilan
 * @created 27/02/2024 - 04:59 pm
 */

@Configuration
public class ApplicationConfig {

    private AdminRepository adminRepository;
    private UserRepository userRepository;

    public ApplicationConfig(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminRepository.findByUsername(username).orElse(null);
            if (admin == null) {
                com.car_rental.car_rental_system.entity.User user = userRepository.findByUsername(username).orElse(null);

                if (user == null) {
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

}
