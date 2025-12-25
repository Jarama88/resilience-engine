package core;

import strategies.RetryStrategy;
import monitoring.ResilienceListener;
import java.util.ArrayList;
import java.util.List;

public class Executor {
    private RetryStrategy strategy;
    private List<ResilienceListener> listeners = new ArrayList<>();

    public Executor(RetryStrategy strategy) {
        this.strategy = strategy;
    }

    public void addListener(ResilienceListener listener) {
        listeners.add(listener);
    }

    public <T> T execute(Task<T> task) throws ResilienceException {
        return strategy.attempt(task, this);
    }

    public void notifyFail(String name, int attempt, Exception e) {
        for (ResilienceListener l : listeners) l.onFail(name, attempt, e);
    }

    public void notifySuccess(String name) {
        for (ResilienceListener l : listeners) l.onSuccess(name);
    }
}