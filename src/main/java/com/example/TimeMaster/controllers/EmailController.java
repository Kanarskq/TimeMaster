package com.example.TimeMaster.controllers;

import com.example.TimeMaster.resources.EmailMessage;
import com.example.TimeMaster.services.EmailSenderService;
import com.example.TimeMaster.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailSenderService emailSenderService;
    private final TaskService taskService;

    public EmailController(EmailSenderService emailSenderService, TaskService taskService) {
        this.emailSenderService = emailSenderService;
        this.taskService = taskService;
    }

    @PostMapping("/send/email")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage){
        this.emailSenderService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getMessage());
        return ResponseEntity.ok("Success");
    }
}
