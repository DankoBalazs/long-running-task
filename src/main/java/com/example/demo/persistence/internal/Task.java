package com.example.demo.persistence.internal;

import com.example.demo.shared.TaskStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Task {
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

    public Task() {
    }

    public Task(String name, Long durationInSeconds) {
        this.name = name;
        this.durationInSeconds = durationInSeconds;
        this.status = TaskStatus.CREATED;
    }

    @PreUpdate
    @PrePersist
    private void updateTimestamps(){
        switch (status){
            case CREATED -> {
                this.created = LocalDateTime.now();
            }
            case IN_PROGRESS -> {
                this.started = LocalDateTime.now();
            }
            case FINISHED, FAILED -> {
                this.finished = LocalDateTime.now();
            }
        }
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
