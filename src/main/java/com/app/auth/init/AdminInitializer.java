package com.app.auth.init;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.auth.entities.Role;
import com.app.auth.entities.User;
import com.app.auth.repository.UserRepository;

@Component
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        long count = userRepository.count(); // will only be called after tables are ready

        if (count == 0) {
            User user = new User();
            user.setFullName("Admin User");
            user.setUserName("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("admin"));
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            user.setDOB(LocalDate.parse("1994-01-30"));
            user.setGender("Male");
            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
            System.out.println("Default admin user created.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
