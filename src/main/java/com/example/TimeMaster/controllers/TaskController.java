package com.example.TimeMaster.controllers;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.TaskRepository;
import com.example.TimeMaster.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping("/usersTasks/{user}")
    public String usersTasks(@PathVariable("user") User user, String selectedDate, Model model) {
        LocalDate selectedLocalDate;

        if (selectedDate != null && !selectedDate.isEmpty()) {
            selectedLocalDate = LocalDate.parse(selectedDate);
        } else {
            selectedLocalDate = LocalDate.now();
        }

        List<Task> tasks = taskRepository.findByUserAndDueDate(user, selectedLocalDate);
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedDate", selectedLocalDate);
        return "usersTasks";
    }

    @GetMapping("/usersTasks/add")
    public String addTask(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByEmail(username);
            model.addAttribute("user", user);
        }
        return "addTask";
    }

    @PostMapping("/usersTasks/add")
    public String addTaskPost(@RequestParam String taskName, @RequestParam LocalDate dueDate, @RequestParam String description, User user, Model model) {
        Task task = new Task(taskName, description, dueDate, user);
        Long userId = user.getId();
        model.addAttribute("user", user);
        if (taskRepository.findByUser(user).size() >= user.getTaskLimit()) {
            return "redirect:/tasks?limitExceeded=true&userId=" + userId;
        }
        taskRepository.save(task);
        return "redirect:/usersTasks/" + userId;
    }

    @GetMapping("/tasks")
    public String getUsersTasks(@RequestParam(name = "limitExceeded") boolean limitExceeded, @RequestParam(name = "user") User user, Model model) {
        model.addAttribute("limitExceeded", limitExceeded);
        model.addAttribute("user", user);
        return "limitExceeded";
    }


    @GetMapping("/usersTasks/task/{id}")
    public String additionalTaskInfo(@PathVariable("id") Long id, Model model, Principal principal) {
        Task task = taskRepository.findById(id).orElse(null);
        model.addAttribute("task", task);
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByEmail(username);
            model.addAttribute("user", user);
        }
        return "task";
    }

    @GetMapping("/usersTasks/task/{id}/edit")
    public String editTaskForm(@PathVariable("id") Long id, Model model, Principal principal) {
        Task task = taskRepository.findById(id).orElse(null);
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByEmail(username);
            model.addAttribute("user", user);
        }

        if (task != null) {
            model.addAttribute("task", task);
            return "editTask";
        } else {
            return "redirect:/usersTasks/task/{id}";
        }
    }

    @PostMapping("/usersTasks/task/{id}/edit")
    public String editTaskSubmit(@PathVariable("id") Long id, @ModelAttribute Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            existingTask.setTaskName(updatedTask.getTaskName());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());

            taskRepository.save(existingTask);
        }

        return "redirect:/usersTasks/task/{id}";
    }

    @PostMapping("/usersTasks/task/{id}/delete")
    public String deleteTaskSubmit(@PathVariable("id") Long id, Model model, Principal principal) {
        taskRepository.deleteById(id);
        String username = principal.getName();
        User user = userRepository.findByEmail(username);
        Long userId = user.getId();
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        return "redirect:/usersTasks/" + userId;
    }

}
