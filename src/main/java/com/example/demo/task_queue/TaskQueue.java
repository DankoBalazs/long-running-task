package com.example.demo.task_queue;

import com.example.demo.persistence.api.Task;
import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.task_executor.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class TaskQueue {
    private static Logger logger = LoggerFactory.getLogger(TaskQueue.class);
    private final TaskPersistenceApi taskPersistenceApi;
    private final Set<Executor> listeners = new HashSet<>();
    private final Queue<Long> tasks = new ArrayBlockingQueue<>(100);


    public TaskQueue(TaskPersistenceApi taskPersistenceApi) {
        this.taskPersistenceApi = taskPersistenceApi;
    }

    public void addTask(String name, Long durationInSeconds){
        Long taskId = taskPersistenceApi.createTask(new Task(name, durationInSeconds));
        tasks.add(taskId);
        logger.info("%s created".formatted(name));
        notifyFirstListener();
    }

    public Long getNext(){
        return tasks.poll();
    }

    public void addListener(Executor executor){
        listeners.add(executor);
        executor.setListener(executor1 -> {
            if(!this.tasks.isEmpty()){
                executor1.execute();
            }
        });
    }

    private void notifyFirstListener(){
        for (Executor listener : this.listeners) {
            if(!listener.isOccupied()){
                listener.execute();
                return;
            }
        }
    }



}
