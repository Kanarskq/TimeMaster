package com.example.TimeMaster.controllers;

import com.example.TimeMaster.models.User;
import com.example.TimeMaster.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userService.saveUser(user)) {
            model.addAttribute("ErrorMessage", "Email is already exists");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        // Perform logout actions if needed

        // Invalidating the session and clearing the authentication
        new SecurityContextLogoutHandler().logout(request, response, null);

        // Redirect to the login page or any other desired destination
        return "redirect:/login";
    }
}
