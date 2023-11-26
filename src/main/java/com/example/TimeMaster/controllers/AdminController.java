package com.example.TimeMaster.controllers;


import com.example.TimeMaster.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/admin/listOfUsers/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin/listOfUsers";
    }

    @PostMapping("/admin/listOfUsers/user/edit/{id}")
    public String updateUserRole(@PathVariable("id") Long id, @RequestParam("newRole") String newRole) {
        // Здійсніть зміну ролі користувача за допомогою сервісу чи репозиторію
        userService.updateUserRole(id, newRole);
        return "redirect:/admin/listOfUsers";
    }

    @GetMapping("/admin/listOfUsers")
    public String listOfUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "listOfUsers";
    }
}
