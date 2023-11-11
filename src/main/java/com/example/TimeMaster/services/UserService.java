package com.example.TimeMaster.services;

import com.example.TimeMaster.enums.Role;
import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean saveUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRole().add(Role.ROLE_USER);
        userRepository.save(user);
        log.info("New user : {}", email);
        return true;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void getUserById(Long id) {
        userRepository.findById(id).orElse(null);
    }

}
