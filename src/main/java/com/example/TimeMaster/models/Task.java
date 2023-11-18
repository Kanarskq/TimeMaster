package com.example.TimeMaster.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name= "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "taskName")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Column(name = "dueDate")
    private LocalDate dueDate;

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public Task(String taskName, String description, LocalDate dueDate, User user) {
        this.taskName = taskName;
        this.description = description;
        this.dueDate = dueDate;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
