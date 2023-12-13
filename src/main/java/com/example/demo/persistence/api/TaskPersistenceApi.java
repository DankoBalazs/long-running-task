package com.example.demo.persistence.api;

public interface TaskPersistenceApi {
    Long createTask(Task task);
    void updateTaskStatus(Long id, TaskStatus taskStatus);
    Task getTask(Long id);
}
