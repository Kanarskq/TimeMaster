package com.example.TimeMaster.controllers;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/usersTasks")
    public String usersTasks(Model model){
        LocalDate today = LocalDate.now();
        model.addAttribute("tasks", taskRepository.findByDueDate(today));
        return "usersTasks";
    }

    @GetMapping("/usersTasks/add")
    public String addTask(){
        return "addTask";
    }

    @PostMapping("/usersTasks/add")
    public String addTaskPost(@RequestParam String taskName, @RequestParam LocalDate dueDate, @RequestParam String description, @RequestParam User user, Model model){
        Task task = new Task(taskName, description, dueDate, user);
        taskRepository.save(task);
        return "redirect:/usersTasks";
    }
}
