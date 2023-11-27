package com.example.TimeMaster.controllers;


import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.UserRepository;
import com.example.TimeMaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping("/admin/listOfUsers/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin/listOfUsers";
    }

    @PostMapping("/admin/listOfUsers/user/edit/{id}")
    public String updateUserRole(@PathVariable("id") Long id, @RequestParam("newRole") String newRole) {
        userService.updateUserRole(id, newRole);
        return "redirect:/admin/listOfUsers";
    }

    @GetMapping("/admin/listOfUsers")
    public String listOfUsers(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        return "listOfUsers";
    }

    @GetMapping("/admin/settings")
    public String settings(Model model, Principal principal){
        String username = principal.getName();
        User user = userRepository.findByEmail(username);
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/admin/settings/setTaskLimit")
    public String setTaskLimit(@RequestParam Integer taskLimit) {
        userService.updateTaskLimit(taskLimit);
        return "redirect:/admin/settings";
    }
}
