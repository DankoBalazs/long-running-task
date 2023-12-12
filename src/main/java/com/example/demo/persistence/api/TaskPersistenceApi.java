package com.example.demo.persistence.api;

import com.example.demo.persistence.internal.Task;
import com.example.demo.shared.TaskStatus;

public interface TaskPersistenceApi {
    Long createTask(String name, Long durationInSeconds);
    void updateTaskStatus(Long id, TaskStatus taskStatus);
    Task getTask(Long id);
}
