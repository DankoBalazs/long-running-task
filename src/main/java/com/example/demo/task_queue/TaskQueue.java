package com.example.demo.task_queue;

import com.example.demo.persistence.api.TaskPersistenceApi;
import com.example.demo.task_executor.Executor;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class TaskQueue {
    private final TaskPersistenceApi taskPersistenceApi;
    private final Set<Executor> listeners = new HashSet<>();
    Queue<Long> tasks = new ArrayBlockingQueue<>(5);

    public TaskQueue(TaskPersistenceApi taskPersistenceApi) {
        this.taskPersistenceApi = taskPersistenceApi;
    }

    public void addTask(String name, Long durationInSeconds){
        Long taskId = taskPersistenceApi.createTask(name, durationInSeconds);
        tasks.add(taskId);
        System.out.printf("%s created\n", name);
        notifyFirstListener();
    }

    public Long getNext(){
        return tasks.poll();
    }

    public void addListener(Executor executor){
        listeners.add(executor);
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
