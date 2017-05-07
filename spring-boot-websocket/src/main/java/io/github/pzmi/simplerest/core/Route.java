package io.github.pzmi.simplerest.core;

public interface Route {
    String path();

    void forward(Message message);

    void register(Observer observer);
}
