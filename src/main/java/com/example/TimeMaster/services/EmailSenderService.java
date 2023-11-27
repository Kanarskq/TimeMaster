package com.example.TimeMaster.services;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.models.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("${Email}");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }

    public void sendReminderEmail(User user, Task task) {
        // Логіка для відправлення нагадування користувачеві
        String to = user.getEmail();
        String subject = "Нагадування: Завдання " + task.getTaskName() + " завтра";
        String message = "Не забудьте виконати завдання \"" + task.getTaskName() + "\" завтра.";

        sendEmail(to, subject, message);
    }

    public void sendReminderEmailsForTomorrow(List<Task> tasks) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        for (Task task : tasks) {
            if (task.getDueDate().isEqual(tomorrow)) {
                sendReminderEmail(task.getUser(), task);
            }
        }
    }
}
