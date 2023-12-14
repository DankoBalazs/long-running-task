package com.example.demo.persistence.internal;

import com.example.demo.persistence.api.Task;
import com.example.demo.persistence.api.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Long durationInSeconds;
    private LocalDateTime created;
    private LocalDateTime started;
    private LocalDateTime finished;
    private String threadName;

    public TaskEntity() {
    }

    public TaskEntity(Task task) {
        this.name = task.getName();
        this.durationInSeconds = task.getDurationInSeconds();
        this.status = task.getStatus();
    }

    @PreUpdate
    @PrePersist
    private void updateTimestamps(){
        switch (status){
            case CREATED -> this.created = LocalDateTime.now();
            case IN_PROGRESS -> this.started = LocalDateTime.now();
            case FINISHED, FAILED -> this.finished = LocalDateTime.now();
        }
        threadName = Thread.currentThread().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }


    public Long getDurationInSeconds() {
        return durationInSeconds;
    }
}
