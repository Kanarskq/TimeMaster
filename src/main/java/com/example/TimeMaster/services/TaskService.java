package com.example.TimeMaster.services;

import com.example.TimeMaster.models.Task;
import com.example.TimeMaster.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public boolean saveTask(Task task) {
        taskRepository.save(task);
        return true;
    }

}
