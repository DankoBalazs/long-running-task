package com.example.demo.task_executor;

import java.util.function.Consumer;

public interface Executor {
    boolean isOccupied();
    void execute();
    void setListener(Consumer<Executor> listener);
}
