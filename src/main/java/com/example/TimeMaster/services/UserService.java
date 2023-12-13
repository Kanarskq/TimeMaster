package com.example.TimeMaster.services;

import com.example.TimeMaster.enums.Role;
import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public boolean updateUserRole(Long id, String newRole) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.getRole().clear();
            user.getRole().add(Role.valueOf(newRole));

            userRepository.save(user);

            log.info("User role updated: {} - new role: {}", user.getEmail(), newRole);
            return true;
        } else {
            log.warn("User not found with id: {}", id);
            return false;
        }
    }

    public boolean updateTaskLimit(Integer taskLimit) {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            user.setTaskLimit(taskLimit);
            userRepository.save(user);

            log.info("Task limit updated for user: {} - new limit: {}", user.getEmail(), taskLimit);
        }

        return true;
    }



    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void findUserById(Long id) {
        userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
