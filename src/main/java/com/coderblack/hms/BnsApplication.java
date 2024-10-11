package com.coderblack.hms;

import com.coderblack.hms.user.Role;
import com.coderblack.hms.user.User;
import com.coderblack.hms.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableJpaAuditing
public class BnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BnsApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@coder.com";
            var existedUser = userRepository.findByEmail(email);

            if (existedUser.isEmpty()) {
                User user = User.builder()
                        .email(email)
                        .firstName("Admin")
                        .lastName("Coder")
                        .role(Role.ADMIN)
                        .isAccountActivate(true)
                        .password(passwordEncoder.encode("password123")).build();
                userRepository.save(user);
            }
        };
    }

}
