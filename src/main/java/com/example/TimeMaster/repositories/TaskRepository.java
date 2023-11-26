package com.example.TimeMaster.repositories;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByDueDate(LocalDate dueDate);

    List<Task> findByUserAndDueDate(User user, LocalDate dueDate);
}
