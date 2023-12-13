package com.example.demo.task_executor;


import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.persistence.api.Task;
import com.example.demo.persistence.api.TaskStatus;
import com.example.demo.task_queue.TaskQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;


public class TaskExecutor implements Executor {

    private Logger logger = LoggerFactory.getLogger("" + this.hashCode());

    private boolean occupied = false;
    private final TaskQueue taskQueue;
    private final TaskPersistenceApi taskPersistenceApi;
    private Consumer<Executor> listener;


    public TaskExecutor(TaskQueue taskQueue, TaskPersistenceApi taskPersistenceApi) {
        this.taskQueue = taskQueue;
        this.taskPersistenceApi = taskPersistenceApi;
    }

    @Override
    public boolean isOccupied() {
        return occupied;
    }

    @Override
    public void execute() {
        occupied = true;
        Long taskId = taskQueue.getNext();
        Task task = taskPersistenceApi.getTask(taskId);
        taskPersistenceApi.updateTaskStatus(taskId, TaskStatus.IN_PROGRESS);
        logger.info("%s started, duration will be: %ss".formatted(task.getName(), task.getDurationInSeconds()));
        try {
            Thread.sleep(task.getDurationInSeconds() * 1000);
            // TODO failed
        } catch (InterruptedException e) {
            taskPersistenceApi.updateTaskStatus(taskId, TaskStatus.FAILED);
           logger.info("%s failed".formatted(task.getName()));
            throw new RuntimeException(e);
        }
        taskPersistenceApi.updateTaskStatus(taskId, TaskStatus.FINISHED);
        logger.info("%s finished".formatted(task.getName()));
        occupied = false;
        listener.accept(this);
    }

    @Override
    public void setListener(Consumer<Executor> listener) {
        this.listener = listener;
    }
}

