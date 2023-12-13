package com.example.demo.persistence.api;

public class Task {

    private final String name;
    private final Long durationInSeconds;
    private final TaskStatus status;

    public Task(String name, Long durationInSeconds) {
        this.name = name;
        this.durationInSeconds = durationInSeconds;
        this.status = TaskStatus.CREATED;
    }

    public Task(String name, Long durationInSeconds, TaskStatus status) {
        this.name = name;
        this.durationInSeconds = durationInSeconds;
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
