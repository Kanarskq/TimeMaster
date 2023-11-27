package com.example.TimeMaster.controllers;

import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ErrorController {
    private final UserRepository userRepository;

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByEmail(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
