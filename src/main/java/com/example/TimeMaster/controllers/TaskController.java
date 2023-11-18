package com.example.TimeMaster.controllers;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.models.User;
import com.example.TimeMaster.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/usersTasks/task/{id}")
    public String additionalTaskInfo(@PathVariable("id") Long id, Model model){
        Task task = taskRepository.findById(id).orElse(null);
        model.addAttribute("task", task);
        return "task";
    }

    @GetMapping("/usersTasks/task/{id}/edit")
    public String editTaskForm(@PathVariable("id") Long id, Model model) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task != null) {
            model.addAttribute("task", task);
            return "editTask";
        } else {
            // Handle the case where the task is not found
            return "redirect:/usersTasks"; // Redirect to the task list
        }
    }

    @PostMapping("/usersTasks/task/{id}/edit")
    public String editTaskSubmit(@PathVariable("id") Long id, @ModelAttribute Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            // Update the existing task with new details
            existingTask.setTaskName(updatedTask.getTaskName());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setDueDate(updatedTask.getDueDate());
            // Update other fields as needed

            taskRepository.save(existingTask);
        }

        return "redirect:/usersTasks/task/{id}";
    }

}
