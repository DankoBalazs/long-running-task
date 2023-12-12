package com.example.demo.persistence.internal;

import com.example.demo.persistence.api.Task;
import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.shared.TaskStatus;
import org.springframework.stereotype.Service;

@Service
class TaskPersistenceApiImpl implements TaskPersistenceApi {
    private final TaskRepository taskRepository;

    public TaskPersistenceApiImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Long createTask(Task task) {
        return taskRepository.save(new TaskEntity(task)).getId();
    }

    @Override
    public void updateTaskStatus(Long id, TaskStatus taskStatus) {
        TaskEntity task = taskRepository.findById(id).orElseThrow();
        task.setStatus(taskStatus);
        taskRepository.save(task);
    }

    @Override
    public Task getTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow();
        return new Task(taskEntity.getName(), taskEntity.getDurationInSeconds(), taskEntity.getStatus());
    }
}
