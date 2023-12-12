package com.example.demo;

import com.example.demo.task_executor.ExecutorFactory;
import com.example.demo.task_queue.TaskQueue;
import org.springframework.stereotype.Service;

@Service
public class Demo {
    private final TaskQueue taskQueue;
    private final ExecutorFactory executorFactory;

    public Demo(TaskQueue taskQueue, ExecutorFactory executorFactory) {
        this.taskQueue = taskQueue;
        this.executorFactory = executorFactory;
        start();
    }

    private void start(){
        taskQueue.addListener(executorFactory.getExecutor());
        taskQueue.addListener(executorFactory.getExecutor());
        for (int i = 1; i <= 100; i++) {
            new MyThread(taskQueue, "Task" + i, 1L).start();
        }

    }


}

class MyThread extends Thread{
    private final TaskQueue taskQueue;
    private final String taskName;
    private final Long taskDurationInSeconds;

    public MyThread(TaskQueue taskQueue, String taskName, Long taskDurationInSeconds) {
        this.taskQueue = taskQueue;
        this.taskName = taskName;
        this.taskDurationInSeconds = taskDurationInSeconds;
    }

    @Override
    public void run() {
        taskQueue.addTask(taskName, taskDurationInSeconds);
    }
}

