package com.example.TimeMaster.resources;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.services.EmailSenderService;
import com.example.TimeMaster.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    private final EmailSenderService emailSenderService;
    private final TaskService taskService;

    @Autowired
    public ScheduledTasks(EmailSenderService emailSenderService, TaskService taskService) {
        this.emailSenderService = emailSenderService;
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 0 0 * * *") // Кожен день о 00:00
    public void sendReminderEmailsForTomorrow() {
        List<Task> allTasks = taskService.getAllTasks(); // Замість цього можна використати ваш спосіб отримання завдань
        emailSenderService.sendReminderEmailsForTomorrow(allTasks);
    }
}
