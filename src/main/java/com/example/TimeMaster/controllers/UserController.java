package com.example.TimeMaster.controllers;

import com.example.TimeMaster.models.User;
import com.example.TimeMaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String saveUser(User user, Model model) {
        if (userService.saveUser(user)) {
            model.addAttribute("ErrorMessage", "Email is already exits");
            return "redirect:/login";
        }
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }
}
