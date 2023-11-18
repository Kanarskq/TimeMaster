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
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/usersTasks")
    public String usersTasks(@RequestParam(required = false) String selectedDate, Model model){
        LocalDate selectedLocalDate;

        if (selectedDate != null && !selectedDate.isEmpty()) {
            selectedLocalDate = LocalDate.parse(selectedDate);
        } else {
            selectedLocalDate = LocalDate.now();
        }

        List<Task> tasks = taskRepository.findByDueDate(selectedLocalDate);
        model.addAttribute("tasks", tasks);
        model.addAttribute("selectedDate", selectedLocalDate);
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
