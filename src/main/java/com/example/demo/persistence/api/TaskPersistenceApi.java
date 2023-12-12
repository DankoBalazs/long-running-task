package com.example.demo.persistence.api;

import com.example.demo.shared.TaskStatus;

public interface TaskPersistenceApi {
    Long createTask(Task task);
    void updateTaskStatus(Long id, TaskStatus taskStatus);
    Task getTask(Long id);
}
