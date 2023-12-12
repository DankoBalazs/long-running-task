package com.example.demo.persistence.internal;

import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.shared.TaskStatus;
import org.springframework.stereotype.Service;

@Service
public class TaskPersistenceApiImpl implements TaskPersistenceApi {
    private final TaskRepository taskRepository;

    public TaskPersistenceApiImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Long createTask(String name, Long durationInSeconds) {
        return taskRepository.save(new Task(name, durationInSeconds)).getId();
    }

    @Override
    public void updateTaskStatus(Long id, TaskStatus taskStatus) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(taskStatus);
        taskRepository.save(task);
    }

    @Override
    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }
}
