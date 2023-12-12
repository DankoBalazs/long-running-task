package com.example.demo.task_executor;

import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.task_queue.TaskQueue;
import org.springframework.stereotype.Service;

@Service
public class ExecutorFactory {
    private final TaskQueue taskQueue;
    private final TaskPersistenceApi taskPersistenceApi;

    public ExecutorFactory(TaskQueue taskQueue, TaskPersistenceApi taskPersistenceApi) {
        this.taskQueue = taskQueue;
        this.taskPersistenceApi = taskPersistenceApi;
    }

    public Executor getExecutor(){
        return new TaskExecutor(taskQueue, taskPersistenceApi);
    }
}
