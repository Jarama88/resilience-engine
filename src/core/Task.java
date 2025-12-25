package core;

public interface Task<T> {
    T execute() throws ResilienceException;
    String getName();
}