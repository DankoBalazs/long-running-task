package com.example.demo.task_executor;


import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.persistence.internal.Task;
import com.example.demo.shared.TaskStatus;
import com.example.demo.task_queue.TaskQueue;


public class TaskExecutor implements Executor {
    private boolean occupied = false;
    private final TaskQueue taskQueue;
    private final TaskPersistenceApi taskPersistenceApi;

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
        System.out.printf("%s started, duration will be: %ss \n", task.getName(), task.getDurationInSeconds());
        try {
            Thread.sleep(task.getDurationInSeconds() * 1000);
            // TODO failed
        } catch (InterruptedException e) {
            taskPersistenceApi.updateTaskStatus(taskId, TaskStatus.FAILED);
            System.out.printf("%s failed\n", task.getName());
            throw new RuntimeException(e);
        }
        taskPersistenceApi.updateTaskStatus(taskId, TaskStatus.FINISHED);
        System.out.printf("%s finished\n", task.getName());
        occupied = false;
    }
}

