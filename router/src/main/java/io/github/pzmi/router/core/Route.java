package io.github.pzmi.router.core;

public interface Route {
    String path();

    void forward(Message message);

    void register(Observer observer);
}
